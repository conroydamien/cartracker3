package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cartracker3App;

import com.mycompany.myapp.domain.Maintrec;
import com.mycompany.myapp.repository.MaintrecRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaintrecResource REST controller.
 *
 * @see MaintrecResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cartracker3App.class)
public class MaintrecResourceIntTest {

    private static final Integer DEFAULT_MILAGE = 1;
    private static final Integer UPDATED_MILAGE = 2;

    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private MaintrecRepository maintrecRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMaintrecMockMvc;

    private Maintrec maintrec;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MaintrecResource maintrecResource = new MaintrecResource();
        ReflectionTestUtils.setField(maintrecResource, "maintrecRepository", maintrecRepository);
        this.restMaintrecMockMvc = MockMvcBuilders.standaloneSetup(maintrecResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maintrec createEntity(EntityManager em) {
        Maintrec maintrec = new Maintrec()
                .milage(DEFAULT_MILAGE)
                .notes(DEFAULT_NOTES);
        return maintrec;
    }

    @Before
    public void initTest() {
        maintrec = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaintrec() throws Exception {
        int databaseSizeBeforeCreate = maintrecRepository.findAll().size();

        // Create the Maintrec

        restMaintrecMockMvc.perform(post("/api/maintrecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(maintrec)))
                .andExpect(status().isCreated());

        // Validate the Maintrec in the database
        List<Maintrec> maintrecs = maintrecRepository.findAll();
        assertThat(maintrecs).hasSize(databaseSizeBeforeCreate + 1);
        Maintrec testMaintrec = maintrecs.get(maintrecs.size() - 1);
        assertThat(testMaintrec.getMilage()).isEqualTo(DEFAULT_MILAGE);
        assertThat(testMaintrec.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void getAllMaintrecs() throws Exception {
        // Initialize the database
        maintrecRepository.saveAndFlush(maintrec);

        // Get all the maintrecs
        restMaintrecMockMvc.perform(get("/api/maintrecs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(maintrec.getId().intValue())))
                .andExpect(jsonPath("$.[*].milage").value(hasItem(DEFAULT_MILAGE)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getMaintrec() throws Exception {
        // Initialize the database
        maintrecRepository.saveAndFlush(maintrec);

        // Get the maintrec
        restMaintrecMockMvc.perform(get("/api/maintrecs/{id}", maintrec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maintrec.getId().intValue()))
            .andExpect(jsonPath("$.milage").value(DEFAULT_MILAGE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaintrec() throws Exception {
        // Get the maintrec
        restMaintrecMockMvc.perform(get("/api/maintrecs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaintrec() throws Exception {
        // Initialize the database
        maintrecRepository.saveAndFlush(maintrec);
        int databaseSizeBeforeUpdate = maintrecRepository.findAll().size();

        // Update the maintrec
        Maintrec updatedMaintrec = maintrecRepository.findOne(maintrec.getId());
        updatedMaintrec
                .milage(UPDATED_MILAGE)
                .notes(UPDATED_NOTES);

        restMaintrecMockMvc.perform(put("/api/maintrecs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMaintrec)))
                .andExpect(status().isOk());

        // Validate the Maintrec in the database
        List<Maintrec> maintrecs = maintrecRepository.findAll();
        assertThat(maintrecs).hasSize(databaseSizeBeforeUpdate);
        Maintrec testMaintrec = maintrecs.get(maintrecs.size() - 1);
        assertThat(testMaintrec.getMilage()).isEqualTo(UPDATED_MILAGE);
        assertThat(testMaintrec.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deleteMaintrec() throws Exception {
        // Initialize the database
        maintrecRepository.saveAndFlush(maintrec);
        int databaseSizeBeforeDelete = maintrecRepository.findAll().size();

        // Get the maintrec
        restMaintrecMockMvc.perform(delete("/api/maintrecs/{id}", maintrec.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Maintrec> maintrecs = maintrecRepository.findAll();
        assertThat(maintrecs).hasSize(databaseSizeBeforeDelete - 1);
    }
}

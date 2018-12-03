package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.DbProcConfig;
import com.apifuze.cockpit.repository.DbProcConfigRepository;
import com.apifuze.cockpit.service.DbProcConfigService;
import com.apifuze.cockpit.service.dto.DbProcConfigDTO;
import com.apifuze.cockpit.service.mapper.DbProcConfigMapper;
import com.apifuze.cockpit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.apifuze.cockpit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DbProcConfigResource REST controller.
 *
 * @see DbProcConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class DbProcConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_P_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_P_SCRIPT = "BBBBBBBBBB";

    @Autowired
    private DbProcConfigRepository dbProcConfigRepository;

    @Autowired
    private DbProcConfigMapper dbProcConfigMapper;

    @Autowired
    private DbProcConfigService dbProcConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDbProcConfigMockMvc;

    private DbProcConfig dbProcConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DbProcConfigResource dbProcConfigResource = new DbProcConfigResource(dbProcConfigService);
        this.restDbProcConfigMockMvc = MockMvcBuilders.standaloneSetup(dbProcConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DbProcConfig createEntity(EntityManager em) {
        DbProcConfig dbProcConfig = new DbProcConfig()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .pScript(DEFAULT_P_SCRIPT);
        return dbProcConfig;
    }

    @Before
    public void initTest() {
        dbProcConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createDbProcConfig() throws Exception {
        int databaseSizeBeforeCreate = dbProcConfigRepository.findAll().size();

        // Create the DbProcConfig
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);
        restDbProcConfigMockMvc.perform(post("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the DbProcConfig in the database
        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeCreate + 1);
        DbProcConfig testDbProcConfig = dbProcConfigList.get(dbProcConfigList.size() - 1);
        assertThat(testDbProcConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDbProcConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDbProcConfig.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testDbProcConfig.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDbProcConfig.getpScript()).isEqualTo(DEFAULT_P_SCRIPT);
    }

    @Test
    @Transactional
    public void createDbProcConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dbProcConfigRepository.findAll().size();

        // Create the DbProcConfig with an existing ID
        dbProcConfig.setId(1L);
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDbProcConfigMockMvc.perform(post("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DbProcConfig in the database
        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dbProcConfigRepository.findAll().size();
        // set the field null
        dbProcConfig.setName(null);

        // Create the DbProcConfig, which fails.
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);

        restDbProcConfigMockMvc.perform(post("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = dbProcConfigRepository.findAll().size();
        // set the field null
        dbProcConfig.setDateCreated(null);

        // Create the DbProcConfig, which fails.
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);

        restDbProcConfigMockMvc.perform(post("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpScriptIsRequired() throws Exception {
        int databaseSizeBeforeTest = dbProcConfigRepository.findAll().size();
        // set the field null
        dbProcConfig.setpScript(null);

        // Create the DbProcConfig, which fails.
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);

        restDbProcConfigMockMvc.perform(post("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDbProcConfigs() throws Exception {
        // Initialize the database
        dbProcConfigRepository.saveAndFlush(dbProcConfig);

        // Get all the dbProcConfigList
        restDbProcConfigMockMvc.perform(get("/api/db-proc-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dbProcConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].pScript").value(hasItem(DEFAULT_P_SCRIPT.toString())));
    }
    
    @Test
    @Transactional
    public void getDbProcConfig() throws Exception {
        // Initialize the database
        dbProcConfigRepository.saveAndFlush(dbProcConfig);

        // Get the dbProcConfig
        restDbProcConfigMockMvc.perform(get("/api/db-proc-configs/{id}", dbProcConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dbProcConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.pScript").value(DEFAULT_P_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDbProcConfig() throws Exception {
        // Get the dbProcConfig
        restDbProcConfigMockMvc.perform(get("/api/db-proc-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDbProcConfig() throws Exception {
        // Initialize the database
        dbProcConfigRepository.saveAndFlush(dbProcConfig);

        int databaseSizeBeforeUpdate = dbProcConfigRepository.findAll().size();

        // Update the dbProcConfig
        DbProcConfig updatedDbProcConfig = dbProcConfigRepository.findById(dbProcConfig.getId()).get();
        // Disconnect from session so that the updates on updatedDbProcConfig are not directly saved in db
        em.detach(updatedDbProcConfig);
        updatedDbProcConfig
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED)
            .pScript(UPDATED_P_SCRIPT);
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(updatedDbProcConfig);

        restDbProcConfigMockMvc.perform(put("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isOk());

        // Validate the DbProcConfig in the database
        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeUpdate);
        DbProcConfig testDbProcConfig = dbProcConfigList.get(dbProcConfigList.size() - 1);
        assertThat(testDbProcConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDbProcConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDbProcConfig.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testDbProcConfig.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDbProcConfig.getpScript()).isEqualTo(UPDATED_P_SCRIPT);
    }

    @Test
    @Transactional
    public void updateNonExistingDbProcConfig() throws Exception {
        int databaseSizeBeforeUpdate = dbProcConfigRepository.findAll().size();

        // Create the DbProcConfig
        DbProcConfigDTO dbProcConfigDTO = dbProcConfigMapper.toDto(dbProcConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDbProcConfigMockMvc.perform(put("/api/db-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dbProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DbProcConfig in the database
        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDbProcConfig() throws Exception {
        // Initialize the database
        dbProcConfigRepository.saveAndFlush(dbProcConfig);

        int databaseSizeBeforeDelete = dbProcConfigRepository.findAll().size();

        // Get the dbProcConfig
        restDbProcConfigMockMvc.perform(delete("/api/db-proc-configs/{id}", dbProcConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DbProcConfig> dbProcConfigList = dbProcConfigRepository.findAll();
        assertThat(dbProcConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DbProcConfig.class);
        DbProcConfig dbProcConfig1 = new DbProcConfig();
        dbProcConfig1.setId(1L);
        DbProcConfig dbProcConfig2 = new DbProcConfig();
        dbProcConfig2.setId(dbProcConfig1.getId());
        assertThat(dbProcConfig1).isEqualTo(dbProcConfig2);
        dbProcConfig2.setId(2L);
        assertThat(dbProcConfig1).isNotEqualTo(dbProcConfig2);
        dbProcConfig1.setId(null);
        assertThat(dbProcConfig1).isNotEqualTo(dbProcConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DbProcConfigDTO.class);
        DbProcConfigDTO dbProcConfigDTO1 = new DbProcConfigDTO();
        dbProcConfigDTO1.setId(1L);
        DbProcConfigDTO dbProcConfigDTO2 = new DbProcConfigDTO();
        assertThat(dbProcConfigDTO1).isNotEqualTo(dbProcConfigDTO2);
        dbProcConfigDTO2.setId(dbProcConfigDTO1.getId());
        assertThat(dbProcConfigDTO1).isEqualTo(dbProcConfigDTO2);
        dbProcConfigDTO2.setId(2L);
        assertThat(dbProcConfigDTO1).isNotEqualTo(dbProcConfigDTO2);
        dbProcConfigDTO1.setId(null);
        assertThat(dbProcConfigDTO1).isNotEqualTo(dbProcConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dbProcConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dbProcConfigMapper.fromId(null)).isNull();
    }
}

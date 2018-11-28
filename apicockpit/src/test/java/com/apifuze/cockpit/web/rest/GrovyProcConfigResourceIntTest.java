package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.GrovyProcConfig;
import com.apifuze.cockpit.repository.GrovyProcConfigRepository;
import com.apifuze.cockpit.service.GrovyProcConfigService;
import com.apifuze.cockpit.service.dto.GrovyProcConfigDTO;
import com.apifuze.cockpit.service.mapper.GrovyProcConfigMapper;
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
 * Test class for the GrovyProcConfigResource REST controller.
 *
 * @see GrovyProcConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class GrovyProcConfigResourceIntTest {

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
    private GrovyProcConfigRepository grovyProcConfigRepository;

    @Autowired
    private GrovyProcConfigMapper grovyProcConfigMapper;

    @Autowired
    private GrovyProcConfigService grovyProcConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrovyProcConfigMockMvc;

    private GrovyProcConfig grovyProcConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrovyProcConfigResource grovyProcConfigResource = new GrovyProcConfigResource(grovyProcConfigService);
        this.restGrovyProcConfigMockMvc = MockMvcBuilders.standaloneSetup(grovyProcConfigResource)
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
    public static GrovyProcConfig createEntity(EntityManager em) {
        GrovyProcConfig grovyProcConfig = new GrovyProcConfig()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .pScript(DEFAULT_P_SCRIPT);
        return grovyProcConfig;
    }

    @Before
    public void initTest() {
        grovyProcConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrovyProcConfig() throws Exception {
        int databaseSizeBeforeCreate = grovyProcConfigRepository.findAll().size();

        // Create the GrovyProcConfig
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);
        restGrovyProcConfigMockMvc.perform(post("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the GrovyProcConfig in the database
        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeCreate + 1);
        GrovyProcConfig testGrovyProcConfig = grovyProcConfigList.get(grovyProcConfigList.size() - 1);
        assertThat(testGrovyProcConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGrovyProcConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGrovyProcConfig.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testGrovyProcConfig.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testGrovyProcConfig.getpScript()).isEqualTo(DEFAULT_P_SCRIPT);
    }

    @Test
    @Transactional
    public void createGrovyProcConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grovyProcConfigRepository.findAll().size();

        // Create the GrovyProcConfig with an existing ID
        grovyProcConfig.setId(1L);
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrovyProcConfigMockMvc.perform(post("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrovyProcConfig in the database
        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = grovyProcConfigRepository.findAll().size();
        // set the field null
        grovyProcConfig.setName(null);

        // Create the GrovyProcConfig, which fails.
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);

        restGrovyProcConfigMockMvc.perform(post("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = grovyProcConfigRepository.findAll().size();
        // set the field null
        grovyProcConfig.setDateCreated(null);

        // Create the GrovyProcConfig, which fails.
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);

        restGrovyProcConfigMockMvc.perform(post("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpScriptIsRequired() throws Exception {
        int databaseSizeBeforeTest = grovyProcConfigRepository.findAll().size();
        // set the field null
        grovyProcConfig.setpScript(null);

        // Create the GrovyProcConfig, which fails.
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);

        restGrovyProcConfigMockMvc.perform(post("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrovyProcConfigs() throws Exception {
        // Initialize the database
        grovyProcConfigRepository.saveAndFlush(grovyProcConfig);

        // Get all the grovyProcConfigList
        restGrovyProcConfigMockMvc.perform(get("/api/grovy-proc-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grovyProcConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].pScript").value(hasItem(DEFAULT_P_SCRIPT.toString())));
    }
    
    @Test
    @Transactional
    public void getGrovyProcConfig() throws Exception {
        // Initialize the database
        grovyProcConfigRepository.saveAndFlush(grovyProcConfig);

        // Get the grovyProcConfig
        restGrovyProcConfigMockMvc.perform(get("/api/grovy-proc-configs/{id}", grovyProcConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grovyProcConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.pScript").value(DEFAULT_P_SCRIPT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrovyProcConfig() throws Exception {
        // Get the grovyProcConfig
        restGrovyProcConfigMockMvc.perform(get("/api/grovy-proc-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrovyProcConfig() throws Exception {
        // Initialize the database
        grovyProcConfigRepository.saveAndFlush(grovyProcConfig);

        int databaseSizeBeforeUpdate = grovyProcConfigRepository.findAll().size();

        // Update the grovyProcConfig
        GrovyProcConfig updatedGrovyProcConfig = grovyProcConfigRepository.findById(grovyProcConfig.getId()).get();
        // Disconnect from session so that the updates on updatedGrovyProcConfig are not directly saved in db
        em.detach(updatedGrovyProcConfig);
        updatedGrovyProcConfig
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED)
            .pScript(UPDATED_P_SCRIPT);
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(updatedGrovyProcConfig);

        restGrovyProcConfigMockMvc.perform(put("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isOk());

        // Validate the GrovyProcConfig in the database
        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeUpdate);
        GrovyProcConfig testGrovyProcConfig = grovyProcConfigList.get(grovyProcConfigList.size() - 1);
        assertThat(testGrovyProcConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGrovyProcConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGrovyProcConfig.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGrovyProcConfig.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testGrovyProcConfig.getpScript()).isEqualTo(UPDATED_P_SCRIPT);
    }

    @Test
    @Transactional
    public void updateNonExistingGrovyProcConfig() throws Exception {
        int databaseSizeBeforeUpdate = grovyProcConfigRepository.findAll().size();

        // Create the GrovyProcConfig
        GrovyProcConfigDTO grovyProcConfigDTO = grovyProcConfigMapper.toDto(grovyProcConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrovyProcConfigMockMvc.perform(put("/api/grovy-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grovyProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrovyProcConfig in the database
        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrovyProcConfig() throws Exception {
        // Initialize the database
        grovyProcConfigRepository.saveAndFlush(grovyProcConfig);

        int databaseSizeBeforeDelete = grovyProcConfigRepository.findAll().size();

        // Get the grovyProcConfig
        restGrovyProcConfigMockMvc.perform(delete("/api/grovy-proc-configs/{id}", grovyProcConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GrovyProcConfig> grovyProcConfigList = grovyProcConfigRepository.findAll();
        assertThat(grovyProcConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrovyProcConfig.class);
        GrovyProcConfig grovyProcConfig1 = new GrovyProcConfig();
        grovyProcConfig1.setId(1L);
        GrovyProcConfig grovyProcConfig2 = new GrovyProcConfig();
        grovyProcConfig2.setId(grovyProcConfig1.getId());
        assertThat(grovyProcConfig1).isEqualTo(grovyProcConfig2);
        grovyProcConfig2.setId(2L);
        assertThat(grovyProcConfig1).isNotEqualTo(grovyProcConfig2);
        grovyProcConfig1.setId(null);
        assertThat(grovyProcConfig1).isNotEqualTo(grovyProcConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrovyProcConfigDTO.class);
        GrovyProcConfigDTO grovyProcConfigDTO1 = new GrovyProcConfigDTO();
        grovyProcConfigDTO1.setId(1L);
        GrovyProcConfigDTO grovyProcConfigDTO2 = new GrovyProcConfigDTO();
        assertThat(grovyProcConfigDTO1).isNotEqualTo(grovyProcConfigDTO2);
        grovyProcConfigDTO2.setId(grovyProcConfigDTO1.getId());
        assertThat(grovyProcConfigDTO1).isEqualTo(grovyProcConfigDTO2);
        grovyProcConfigDTO2.setId(2L);
        assertThat(grovyProcConfigDTO1).isNotEqualTo(grovyProcConfigDTO2);
        grovyProcConfigDTO1.setId(null);
        assertThat(grovyProcConfigDTO1).isNotEqualTo(grovyProcConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(grovyProcConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(grovyProcConfigMapper.fromId(null)).isNull();
    }
}

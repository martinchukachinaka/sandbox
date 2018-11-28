package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiSvcProcConfig;
import com.apifuze.cockpit.repository.ApiSvcProcConfigRepository;
import com.apifuze.cockpit.service.ApiSvcProcConfigService;
import com.apifuze.cockpit.service.dto.ApiSvcProcConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiSvcProcConfigMapper;
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

import com.apifuze.cockpit.domain.enumeration.ProcessorType;
/**
 * Test class for the ApiSvcProcConfigResource REST controller.
 *
 * @see ApiSvcProcConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiSvcProcConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ProcessorType DEFAULT_PROCESSOR_TYPE = ProcessorType.DB;
    private static final ProcessorType UPDATED_PROCESSOR_TYPE = ProcessorType.HTTP;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private ApiSvcProcConfigRepository apiSvcProcConfigRepository;

    @Autowired
    private ApiSvcProcConfigMapper apiSvcProcConfigMapper;

    @Autowired
    private ApiSvcProcConfigService apiSvcProcConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiSvcProcConfigMockMvc;

    private ApiSvcProcConfig apiSvcProcConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiSvcProcConfigResource apiSvcProcConfigResource = new ApiSvcProcConfigResource(apiSvcProcConfigService);
        this.restApiSvcProcConfigMockMvc = MockMvcBuilders.standaloneSetup(apiSvcProcConfigResource)
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
    public static ApiSvcProcConfig createEntity(EntityManager em) {
        ApiSvcProcConfig apiSvcProcConfig = new ApiSvcProcConfig()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .processorType(DEFAULT_PROCESSOR_TYPE)
            .order(DEFAULT_ORDER);
        return apiSvcProcConfig;
    }

    @Before
    public void initTest() {
        apiSvcProcConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiSvcProcConfig() throws Exception {
        int databaseSizeBeforeCreate = apiSvcProcConfigRepository.findAll().size();

        // Create the ApiSvcProcConfig
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);
        restApiSvcProcConfigMockMvc.perform(post("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiSvcProcConfig in the database
        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ApiSvcProcConfig testApiSvcProcConfig = apiSvcProcConfigList.get(apiSvcProcConfigList.size() - 1);
        assertThat(testApiSvcProcConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiSvcProcConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiSvcProcConfig.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiSvcProcConfig.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testApiSvcProcConfig.getProcessorType()).isEqualTo(DEFAULT_PROCESSOR_TYPE);
        assertThat(testApiSvcProcConfig.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createApiSvcProcConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiSvcProcConfigRepository.findAll().size();

        // Create the ApiSvcProcConfig with an existing ID
        apiSvcProcConfig.setId(1L);
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiSvcProcConfigMockMvc.perform(post("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiSvcProcConfig in the database
        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiSvcProcConfigRepository.findAll().size();
        // set the field null
        apiSvcProcConfig.setName(null);

        // Create the ApiSvcProcConfig, which fails.
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);

        restApiSvcProcConfigMockMvc.perform(post("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiSvcProcConfigRepository.findAll().size();
        // set the field null
        apiSvcProcConfig.setDateCreated(null);

        // Create the ApiSvcProcConfig, which fails.
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);

        restApiSvcProcConfigMockMvc.perform(post("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcessorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiSvcProcConfigRepository.findAll().size();
        // set the field null
        apiSvcProcConfig.setProcessorType(null);

        // Create the ApiSvcProcConfig, which fails.
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);

        restApiSvcProcConfigMockMvc.perform(post("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiSvcProcConfigs() throws Exception {
        // Initialize the database
        apiSvcProcConfigRepository.saveAndFlush(apiSvcProcConfig);

        // Get all the apiSvcProcConfigList
        restApiSvcProcConfigMockMvc.perform(get("/api/api-svc-proc-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiSvcProcConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].processorType").value(hasItem(DEFAULT_PROCESSOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getApiSvcProcConfig() throws Exception {
        // Initialize the database
        apiSvcProcConfigRepository.saveAndFlush(apiSvcProcConfig);

        // Get the apiSvcProcConfig
        restApiSvcProcConfigMockMvc.perform(get("/api/api-svc-proc-configs/{id}", apiSvcProcConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiSvcProcConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.processorType").value(DEFAULT_PROCESSOR_TYPE.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingApiSvcProcConfig() throws Exception {
        // Get the apiSvcProcConfig
        restApiSvcProcConfigMockMvc.perform(get("/api/api-svc-proc-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiSvcProcConfig() throws Exception {
        // Initialize the database
        apiSvcProcConfigRepository.saveAndFlush(apiSvcProcConfig);

        int databaseSizeBeforeUpdate = apiSvcProcConfigRepository.findAll().size();

        // Update the apiSvcProcConfig
        ApiSvcProcConfig updatedApiSvcProcConfig = apiSvcProcConfigRepository.findById(apiSvcProcConfig.getId()).get();
        // Disconnect from session so that the updates on updatedApiSvcProcConfig are not directly saved in db
        em.detach(updatedApiSvcProcConfig);
        updatedApiSvcProcConfig
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED)
            .processorType(UPDATED_PROCESSOR_TYPE)
            .order(UPDATED_ORDER);
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(updatedApiSvcProcConfig);

        restApiSvcProcConfigMockMvc.perform(put("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ApiSvcProcConfig in the database
        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeUpdate);
        ApiSvcProcConfig testApiSvcProcConfig = apiSvcProcConfigList.get(apiSvcProcConfigList.size() - 1);
        assertThat(testApiSvcProcConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiSvcProcConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiSvcProcConfig.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiSvcProcConfig.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testApiSvcProcConfig.getProcessorType()).isEqualTo(UPDATED_PROCESSOR_TYPE);
        assertThat(testApiSvcProcConfig.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingApiSvcProcConfig() throws Exception {
        int databaseSizeBeforeUpdate = apiSvcProcConfigRepository.findAll().size();

        // Create the ApiSvcProcConfig
        ApiSvcProcConfigDTO apiSvcProcConfigDTO = apiSvcProcConfigMapper.toDto(apiSvcProcConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiSvcProcConfigMockMvc.perform(put("/api/api-svc-proc-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiSvcProcConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiSvcProcConfig in the database
        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiSvcProcConfig() throws Exception {
        // Initialize the database
        apiSvcProcConfigRepository.saveAndFlush(apiSvcProcConfig);

        int databaseSizeBeforeDelete = apiSvcProcConfigRepository.findAll().size();

        // Get the apiSvcProcConfig
        restApiSvcProcConfigMockMvc.perform(delete("/api/api-svc-proc-configs/{id}", apiSvcProcConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiSvcProcConfig> apiSvcProcConfigList = apiSvcProcConfigRepository.findAll();
        assertThat(apiSvcProcConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiSvcProcConfig.class);
        ApiSvcProcConfig apiSvcProcConfig1 = new ApiSvcProcConfig();
        apiSvcProcConfig1.setId(1L);
        ApiSvcProcConfig apiSvcProcConfig2 = new ApiSvcProcConfig();
        apiSvcProcConfig2.setId(apiSvcProcConfig1.getId());
        assertThat(apiSvcProcConfig1).isEqualTo(apiSvcProcConfig2);
        apiSvcProcConfig2.setId(2L);
        assertThat(apiSvcProcConfig1).isNotEqualTo(apiSvcProcConfig2);
        apiSvcProcConfig1.setId(null);
        assertThat(apiSvcProcConfig1).isNotEqualTo(apiSvcProcConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiSvcProcConfigDTO.class);
        ApiSvcProcConfigDTO apiSvcProcConfigDTO1 = new ApiSvcProcConfigDTO();
        apiSvcProcConfigDTO1.setId(1L);
        ApiSvcProcConfigDTO apiSvcProcConfigDTO2 = new ApiSvcProcConfigDTO();
        assertThat(apiSvcProcConfigDTO1).isNotEqualTo(apiSvcProcConfigDTO2);
        apiSvcProcConfigDTO2.setId(apiSvcProcConfigDTO1.getId());
        assertThat(apiSvcProcConfigDTO1).isEqualTo(apiSvcProcConfigDTO2);
        apiSvcProcConfigDTO2.setId(2L);
        assertThat(apiSvcProcConfigDTO1).isNotEqualTo(apiSvcProcConfigDTO2);
        apiSvcProcConfigDTO1.setId(null);
        assertThat(apiSvcProcConfigDTO1).isNotEqualTo(apiSvcProcConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiSvcProcConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiSvcProcConfigMapper.fromId(null)).isNull();
    }
}

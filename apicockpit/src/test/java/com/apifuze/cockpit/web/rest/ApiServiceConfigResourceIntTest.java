package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiServiceConfig;
import com.apifuze.cockpit.repository.ApiServiceConfigRepository;
import com.apifuze.cockpit.service.ApiServiceConfigService;
import com.apifuze.cockpit.service.dto.ApiServiceConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiServiceConfigMapper;
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
 * Test class for the ApiServiceConfigResource REST controller.
 *
 * @see ApiServiceConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiServiceConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_DOC_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApiServiceConfigRepository apiServiceConfigRepository;

    @Autowired
    private ApiServiceConfigMapper apiServiceConfigMapper;

    @Autowired
    private ApiServiceConfigService apiServiceConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiServiceConfigMockMvc;

    private ApiServiceConfig apiServiceConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiServiceConfigResource apiServiceConfigResource = new ApiServiceConfigResource(apiServiceConfigService);
        this.restApiServiceConfigMockMvc = MockMvcBuilders.standaloneSetup(apiServiceConfigResource)
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
    public static ApiServiceConfig createEntity(EntityManager em) {
        ApiServiceConfig apiServiceConfig = new ApiServiceConfig()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .serviceUrl(DEFAULT_SERVICE_URL)
            .serviceDocUrl(DEFAULT_SERVICE_DOC_URL)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED);
        return apiServiceConfig;
    }

    @Before
    public void initTest() {
        apiServiceConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiServiceConfig() throws Exception {
        int databaseSizeBeforeCreate = apiServiceConfigRepository.findAll().size();

        // Create the ApiServiceConfig
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);
        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiServiceConfig in the database
        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ApiServiceConfig testApiServiceConfig = apiServiceConfigList.get(apiServiceConfigList.size() - 1);
        assertThat(testApiServiceConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiServiceConfig.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiServiceConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiServiceConfig.getServiceUrl()).isEqualTo(DEFAULT_SERVICE_URL);
        assertThat(testApiServiceConfig.getServiceDocUrl()).isEqualTo(DEFAULT_SERVICE_DOC_URL);
        assertThat(testApiServiceConfig.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiServiceConfig.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createApiServiceConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiServiceConfigRepository.findAll().size();

        // Create the ApiServiceConfig with an existing ID
        apiServiceConfig.setId(1L);
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceConfig in the database
        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceConfigRepository.findAll().size();
        // set the field null
        apiServiceConfig.setName(null);

        // Create the ApiServiceConfig, which fails.
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceConfigRepository.findAll().size();
        // set the field null
        apiServiceConfig.setCode(null);

        // Create the ApiServiceConfig, which fails.
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceConfigRepository.findAll().size();
        // set the field null
        apiServiceConfig.setServiceUrl(null);

        // Create the ApiServiceConfig, which fails.
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceDocUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceConfigRepository.findAll().size();
        // set the field null
        apiServiceConfig.setServiceDocUrl(null);

        // Create the ApiServiceConfig, which fails.
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceConfigRepository.findAll().size();
        // set the field null
        apiServiceConfig.setDateCreated(null);

        // Create the ApiServiceConfig, which fails.
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        restApiServiceConfigMockMvc.perform(post("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiServiceConfigs() throws Exception {
        // Initialize the database
        apiServiceConfigRepository.saveAndFlush(apiServiceConfig);

        // Get all the apiServiceConfigList
        restApiServiceConfigMockMvc.perform(get("/api/api-service-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiServiceConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].serviceUrl").value(hasItem(DEFAULT_SERVICE_URL.toString())))
            .andExpect(jsonPath("$.[*].serviceDocUrl").value(hasItem(DEFAULT_SERVICE_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getApiServiceConfig() throws Exception {
        // Initialize the database
        apiServiceConfigRepository.saveAndFlush(apiServiceConfig);

        // Get the apiServiceConfig
        restApiServiceConfigMockMvc.perform(get("/api/api-service-configs/{id}", apiServiceConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiServiceConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.serviceUrl").value(DEFAULT_SERVICE_URL.toString()))
            .andExpect(jsonPath("$.serviceDocUrl").value(DEFAULT_SERVICE_DOC_URL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiServiceConfig() throws Exception {
        // Get the apiServiceConfig
        restApiServiceConfigMockMvc.perform(get("/api/api-service-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiServiceConfig() throws Exception {
        // Initialize the database
        apiServiceConfigRepository.saveAndFlush(apiServiceConfig);

        int databaseSizeBeforeUpdate = apiServiceConfigRepository.findAll().size();

        // Update the apiServiceConfig
        ApiServiceConfig updatedApiServiceConfig = apiServiceConfigRepository.findById(apiServiceConfig.getId()).get();
        // Disconnect from session so that the updates on updatedApiServiceConfig are not directly saved in db
        em.detach(updatedApiServiceConfig);
        updatedApiServiceConfig
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .serviceUrl(UPDATED_SERVICE_URL)
            .serviceDocUrl(UPDATED_SERVICE_DOC_URL)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED);
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(updatedApiServiceConfig);

        restApiServiceConfigMockMvc.perform(put("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ApiServiceConfig in the database
        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeUpdate);
        ApiServiceConfig testApiServiceConfig = apiServiceConfigList.get(apiServiceConfigList.size() - 1);
        assertThat(testApiServiceConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiServiceConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiServiceConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiServiceConfig.getServiceUrl()).isEqualTo(UPDATED_SERVICE_URL);
        assertThat(testApiServiceConfig.getServiceDocUrl()).isEqualTo(UPDATED_SERVICE_DOC_URL);
        assertThat(testApiServiceConfig.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiServiceConfig.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingApiServiceConfig() throws Exception {
        int databaseSizeBeforeUpdate = apiServiceConfigRepository.findAll().size();

        // Create the ApiServiceConfig
        ApiServiceConfigDTO apiServiceConfigDTO = apiServiceConfigMapper.toDto(apiServiceConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiServiceConfigMockMvc.perform(put("/api/api-service-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceConfig in the database
        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiServiceConfig() throws Exception {
        // Initialize the database
        apiServiceConfigRepository.saveAndFlush(apiServiceConfig);

        int databaseSizeBeforeDelete = apiServiceConfigRepository.findAll().size();

        // Get the apiServiceConfig
        restApiServiceConfigMockMvc.perform(delete("/api/api-service-configs/{id}", apiServiceConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiServiceConfig> apiServiceConfigList = apiServiceConfigRepository.findAll();
        assertThat(apiServiceConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiServiceConfig.class);
        ApiServiceConfig apiServiceConfig1 = new ApiServiceConfig();
        apiServiceConfig1.setId(1L);
        ApiServiceConfig apiServiceConfig2 = new ApiServiceConfig();
        apiServiceConfig2.setId(apiServiceConfig1.getId());
        assertThat(apiServiceConfig1).isEqualTo(apiServiceConfig2);
        apiServiceConfig2.setId(2L);
        assertThat(apiServiceConfig1).isNotEqualTo(apiServiceConfig2);
        apiServiceConfig1.setId(null);
        assertThat(apiServiceConfig1).isNotEqualTo(apiServiceConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiServiceConfigDTO.class);
        ApiServiceConfigDTO apiServiceConfigDTO1 = new ApiServiceConfigDTO();
        apiServiceConfigDTO1.setId(1L);
        ApiServiceConfigDTO apiServiceConfigDTO2 = new ApiServiceConfigDTO();
        assertThat(apiServiceConfigDTO1).isNotEqualTo(apiServiceConfigDTO2);
        apiServiceConfigDTO2.setId(apiServiceConfigDTO1.getId());
        assertThat(apiServiceConfigDTO1).isEqualTo(apiServiceConfigDTO2);
        apiServiceConfigDTO2.setId(2L);
        assertThat(apiServiceConfigDTO1).isNotEqualTo(apiServiceConfigDTO2);
        apiServiceConfigDTO1.setId(null);
        assertThat(apiServiceConfigDTO1).isNotEqualTo(apiServiceConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiServiceConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiServiceConfigMapper.fromId(null)).isNull();
    }
}

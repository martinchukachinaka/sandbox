package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiProjectAuthConfig;
import com.apifuze.cockpit.repository.ApiProjectAuthConfigRepository;
import com.apifuze.cockpit.service.ApiProjectAuthConfigService;
import com.apifuze.cockpit.service.dto.ApiProjectAuthConfigDTO;
import com.apifuze.cockpit.service.mapper.ApiProjectAuthConfigMapper;
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
 * Test class for the ApiProjectAuthConfigResource REST controller.
 *
 * @see ApiProjectAuthConfigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiProjectAuthConfigResourceIntTest {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_SECRET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApiProjectAuthConfigRepository apiProjectAuthConfigRepository;

    @Autowired
    private ApiProjectAuthConfigMapper apiProjectAuthConfigMapper;

    @Autowired
    private ApiProjectAuthConfigService apiProjectAuthConfigService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiProjectAuthConfigMockMvc;

    private ApiProjectAuthConfig apiProjectAuthConfig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiProjectAuthConfigResource apiProjectAuthConfigResource = new ApiProjectAuthConfigResource(apiProjectAuthConfigService);
        this.restApiProjectAuthConfigMockMvc = MockMvcBuilders.standaloneSetup(apiProjectAuthConfigResource)
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
    public static ApiProjectAuthConfig createEntity(EntityManager em) {
        ApiProjectAuthConfig apiProjectAuthConfig = new ApiProjectAuthConfig()
            .clientId(DEFAULT_CLIENT_ID)
            .clientSecret(DEFAULT_CLIENT_SECRET)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED);
        return apiProjectAuthConfig;
    }

    @Before
    public void initTest() {
        apiProjectAuthConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiProjectAuthConfig() throws Exception {
        int databaseSizeBeforeCreate = apiProjectAuthConfigRepository.findAll().size();

        // Create the ApiProjectAuthConfig
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = apiProjectAuthConfigMapper.toDto(apiProjectAuthConfig);
        restApiProjectAuthConfigMockMvc.perform(post("/api/api-project-auth-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiProjectAuthConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiProjectAuthConfig in the database
        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ApiProjectAuthConfig testApiProjectAuthConfig = apiProjectAuthConfigList.get(apiProjectAuthConfigList.size() - 1);
        assertThat(testApiProjectAuthConfig.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testApiProjectAuthConfig.getClientSecret()).isEqualTo(DEFAULT_CLIENT_SECRET);
        assertThat(testApiProjectAuthConfig.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiProjectAuthConfig.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createApiProjectAuthConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiProjectAuthConfigRepository.findAll().size();

        // Create the ApiProjectAuthConfig with an existing ID
        apiProjectAuthConfig.setId(1L);
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = apiProjectAuthConfigMapper.toDto(apiProjectAuthConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiProjectAuthConfigMockMvc.perform(post("/api/api-project-auth-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiProjectAuthConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiProjectAuthConfig in the database
        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiProjectAuthConfigRepository.findAll().size();
        // set the field null
        apiProjectAuthConfig.setDateCreated(null);

        // Create the ApiProjectAuthConfig, which fails.
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = apiProjectAuthConfigMapper.toDto(apiProjectAuthConfig);

        restApiProjectAuthConfigMockMvc.perform(post("/api/api-project-auth-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiProjectAuthConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiProjectAuthConfigs() throws Exception {
        // Initialize the database
        apiProjectAuthConfigRepository.saveAndFlush(apiProjectAuthConfig);

        // Get all the apiProjectAuthConfigList
        restApiProjectAuthConfigMockMvc.perform(get("/api/api-project-auth-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiProjectAuthConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].clientSecret").value(hasItem(DEFAULT_CLIENT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getApiProjectAuthConfig() throws Exception {
        // Initialize the database
        apiProjectAuthConfigRepository.saveAndFlush(apiProjectAuthConfig);

        // Get the apiProjectAuthConfig
        restApiProjectAuthConfigMockMvc.perform(get("/api/api-project-auth-configs/{id}", apiProjectAuthConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiProjectAuthConfig.getId().intValue()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.clientSecret").value(DEFAULT_CLIENT_SECRET.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiProjectAuthConfig() throws Exception {
        // Get the apiProjectAuthConfig
        restApiProjectAuthConfigMockMvc.perform(get("/api/api-project-auth-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiProjectAuthConfig() throws Exception {
        // Initialize the database
        apiProjectAuthConfigRepository.saveAndFlush(apiProjectAuthConfig);

        int databaseSizeBeforeUpdate = apiProjectAuthConfigRepository.findAll().size();

        // Update the apiProjectAuthConfig
        ApiProjectAuthConfig updatedApiProjectAuthConfig = apiProjectAuthConfigRepository.findById(apiProjectAuthConfig.getId()).get();
        // Disconnect from session so that the updates on updatedApiProjectAuthConfig are not directly saved in db
        em.detach(updatedApiProjectAuthConfig);
        updatedApiProjectAuthConfig
            .clientId(UPDATED_CLIENT_ID)
            .clientSecret(UPDATED_CLIENT_SECRET)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED);
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = apiProjectAuthConfigMapper.toDto(updatedApiProjectAuthConfig);

        restApiProjectAuthConfigMockMvc.perform(put("/api/api-project-auth-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiProjectAuthConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ApiProjectAuthConfig in the database
        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeUpdate);
        ApiProjectAuthConfig testApiProjectAuthConfig = apiProjectAuthConfigList.get(apiProjectAuthConfigList.size() - 1);
        assertThat(testApiProjectAuthConfig.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testApiProjectAuthConfig.getClientSecret()).isEqualTo(UPDATED_CLIENT_SECRET);
        assertThat(testApiProjectAuthConfig.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiProjectAuthConfig.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingApiProjectAuthConfig() throws Exception {
        int databaseSizeBeforeUpdate = apiProjectAuthConfigRepository.findAll().size();

        // Create the ApiProjectAuthConfig
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO = apiProjectAuthConfigMapper.toDto(apiProjectAuthConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiProjectAuthConfigMockMvc.perform(put("/api/api-project-auth-configs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiProjectAuthConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiProjectAuthConfig in the database
        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiProjectAuthConfig() throws Exception {
        // Initialize the database
        apiProjectAuthConfigRepository.saveAndFlush(apiProjectAuthConfig);

        int databaseSizeBeforeDelete = apiProjectAuthConfigRepository.findAll().size();

        // Get the apiProjectAuthConfig
        restApiProjectAuthConfigMockMvc.perform(delete("/api/api-project-auth-configs/{id}", apiProjectAuthConfig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiProjectAuthConfig> apiProjectAuthConfigList = apiProjectAuthConfigRepository.findAll();
        assertThat(apiProjectAuthConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiProjectAuthConfig.class);
        ApiProjectAuthConfig apiProjectAuthConfig1 = new ApiProjectAuthConfig();
        apiProjectAuthConfig1.setId(1L);
        ApiProjectAuthConfig apiProjectAuthConfig2 = new ApiProjectAuthConfig();
        apiProjectAuthConfig2.setId(apiProjectAuthConfig1.getId());
        assertThat(apiProjectAuthConfig1).isEqualTo(apiProjectAuthConfig2);
        apiProjectAuthConfig2.setId(2L);
        assertThat(apiProjectAuthConfig1).isNotEqualTo(apiProjectAuthConfig2);
        apiProjectAuthConfig1.setId(null);
        assertThat(apiProjectAuthConfig1).isNotEqualTo(apiProjectAuthConfig2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiProjectAuthConfigDTO.class);
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO1 = new ApiProjectAuthConfigDTO();
        apiProjectAuthConfigDTO1.setId(1L);
        ApiProjectAuthConfigDTO apiProjectAuthConfigDTO2 = new ApiProjectAuthConfigDTO();
        assertThat(apiProjectAuthConfigDTO1).isNotEqualTo(apiProjectAuthConfigDTO2);
        apiProjectAuthConfigDTO2.setId(apiProjectAuthConfigDTO1.getId());
        assertThat(apiProjectAuthConfigDTO1).isEqualTo(apiProjectAuthConfigDTO2);
        apiProjectAuthConfigDTO2.setId(2L);
        assertThat(apiProjectAuthConfigDTO1).isNotEqualTo(apiProjectAuthConfigDTO2);
        apiProjectAuthConfigDTO1.setId(null);
        assertThat(apiProjectAuthConfigDTO1).isNotEqualTo(apiProjectAuthConfigDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiProjectAuthConfigMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiProjectAuthConfigMapper.fromId(null)).isNull();
    }
}

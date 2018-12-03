package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiPublisherProfile;
import com.apifuze.cockpit.repository.ApiPublisherProfileRepository;
import com.apifuze.cockpit.service.ApiPublisherProfileService;
import com.apifuze.cockpit.service.dto.ApiPublisherProfileDTO;
import com.apifuze.cockpit.service.mapper.ApiPublisherProfileMapper;
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
 * Test class for the ApiPublisherProfileResource REST controller.
 *
 * @see ApiPublisherProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiPublisherProfileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_API_BASE_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_BASE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_API_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_DOC_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApiPublisherProfileRepository apiPublisherProfileRepository;

    @Autowired
    private ApiPublisherProfileMapper apiPublisherProfileMapper;

    @Autowired
    private ApiPublisherProfileService apiPublisherProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiPublisherProfileMockMvc;

    private ApiPublisherProfile apiPublisherProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiPublisherProfileResource apiPublisherProfileResource = new ApiPublisherProfileResource(apiPublisherProfileService);
        this.restApiPublisherProfileMockMvc = MockMvcBuilders.standaloneSetup(apiPublisherProfileResource)
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
    public static ApiPublisherProfile createEntity(EntityManager em) {
        ApiPublisherProfile apiPublisherProfile = new ApiPublisherProfile()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .apiBaseUrl(DEFAULT_API_BASE_URL)
            .apiDocUrl(DEFAULT_API_DOC_URL)
            .dateCreated(DEFAULT_DATE_CREATED);
        return apiPublisherProfile;
    }

    @Before
    public void initTest() {
        apiPublisherProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiPublisherProfile() throws Exception {
        int databaseSizeBeforeCreate = apiPublisherProfileRepository.findAll().size();

        // Create the ApiPublisherProfile
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);
        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiPublisherProfile in the database
        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ApiPublisherProfile testApiPublisherProfile = apiPublisherProfileList.get(apiPublisherProfileList.size() - 1);
        assertThat(testApiPublisherProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiPublisherProfile.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiPublisherProfile.getApiBaseUrl()).isEqualTo(DEFAULT_API_BASE_URL);
        assertThat(testApiPublisherProfile.getApiDocUrl()).isEqualTo(DEFAULT_API_DOC_URL);
        assertThat(testApiPublisherProfile.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createApiPublisherProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiPublisherProfileRepository.findAll().size();

        // Create the ApiPublisherProfile with an existing ID
        apiPublisherProfile.setId(1L);
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPublisherProfile in the database
        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiPublisherProfileRepository.findAll().size();
        // set the field null
        apiPublisherProfile.setName(null);

        // Create the ApiPublisherProfile, which fails.
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiBaseUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiPublisherProfileRepository.findAll().size();
        // set the field null
        apiPublisherProfile.setApiBaseUrl(null);

        // Create the ApiPublisherProfile, which fails.
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiDocUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiPublisherProfileRepository.findAll().size();
        // set the field null
        apiPublisherProfile.setApiDocUrl(null);

        // Create the ApiPublisherProfile, which fails.
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiPublisherProfileRepository.findAll().size();
        // set the field null
        apiPublisherProfile.setDateCreated(null);

        // Create the ApiPublisherProfile, which fails.
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        restApiPublisherProfileMockMvc.perform(post("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiPublisherProfiles() throws Exception {
        // Initialize the database
        apiPublisherProfileRepository.saveAndFlush(apiPublisherProfile);

        // Get all the apiPublisherProfileList
        restApiPublisherProfileMockMvc.perform(get("/api/api-publisher-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiPublisherProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].apiBaseUrl").value(hasItem(DEFAULT_API_BASE_URL.toString())))
            .andExpect(jsonPath("$.[*].apiDocUrl").value(hasItem(DEFAULT_API_DOC_URL.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getApiPublisherProfile() throws Exception {
        // Initialize the database
        apiPublisherProfileRepository.saveAndFlush(apiPublisherProfile);

        // Get the apiPublisherProfile
        restApiPublisherProfileMockMvc.perform(get("/api/api-publisher-profiles/{id}", apiPublisherProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiPublisherProfile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.apiBaseUrl").value(DEFAULT_API_BASE_URL.toString()))
            .andExpect(jsonPath("$.apiDocUrl").value(DEFAULT_API_DOC_URL.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiPublisherProfile() throws Exception {
        // Get the apiPublisherProfile
        restApiPublisherProfileMockMvc.perform(get("/api/api-publisher-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiPublisherProfile() throws Exception {
        // Initialize the database
        apiPublisherProfileRepository.saveAndFlush(apiPublisherProfile);

        int databaseSizeBeforeUpdate = apiPublisherProfileRepository.findAll().size();

        // Update the apiPublisherProfile
        ApiPublisherProfile updatedApiPublisherProfile = apiPublisherProfileRepository.findById(apiPublisherProfile.getId()).get();
        // Disconnect from session so that the updates on updatedApiPublisherProfile are not directly saved in db
        em.detach(updatedApiPublisherProfile);
        updatedApiPublisherProfile
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .apiBaseUrl(UPDATED_API_BASE_URL)
            .apiDocUrl(UPDATED_API_DOC_URL)
            .dateCreated(UPDATED_DATE_CREATED);
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(updatedApiPublisherProfile);

        restApiPublisherProfileMockMvc.perform(put("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isOk());

        // Validate the ApiPublisherProfile in the database
        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeUpdate);
        ApiPublisherProfile testApiPublisherProfile = apiPublisherProfileList.get(apiPublisherProfileList.size() - 1);
        assertThat(testApiPublisherProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiPublisherProfile.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiPublisherProfile.getApiBaseUrl()).isEqualTo(UPDATED_API_BASE_URL);
        assertThat(testApiPublisherProfile.getApiDocUrl()).isEqualTo(UPDATED_API_DOC_URL);
        assertThat(testApiPublisherProfile.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingApiPublisherProfile() throws Exception {
        int databaseSizeBeforeUpdate = apiPublisherProfileRepository.findAll().size();

        // Create the ApiPublisherProfile
        ApiPublisherProfileDTO apiPublisherProfileDTO = apiPublisherProfileMapper.toDto(apiPublisherProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiPublisherProfileMockMvc.perform(put("/api/api-publisher-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiPublisherProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiPublisherProfile in the database
        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiPublisherProfile() throws Exception {
        // Initialize the database
        apiPublisherProfileRepository.saveAndFlush(apiPublisherProfile);

        int databaseSizeBeforeDelete = apiPublisherProfileRepository.findAll().size();

        // Get the apiPublisherProfile
        restApiPublisherProfileMockMvc.perform(delete("/api/api-publisher-profiles/{id}", apiPublisherProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiPublisherProfile> apiPublisherProfileList = apiPublisherProfileRepository.findAll();
        assertThat(apiPublisherProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiPublisherProfile.class);
        ApiPublisherProfile apiPublisherProfile1 = new ApiPublisherProfile();
        apiPublisherProfile1.setId(1L);
        ApiPublisherProfile apiPublisherProfile2 = new ApiPublisherProfile();
        apiPublisherProfile2.setId(apiPublisherProfile1.getId());
        assertThat(apiPublisherProfile1).isEqualTo(apiPublisherProfile2);
        apiPublisherProfile2.setId(2L);
        assertThat(apiPublisherProfile1).isNotEqualTo(apiPublisherProfile2);
        apiPublisherProfile1.setId(null);
        assertThat(apiPublisherProfile1).isNotEqualTo(apiPublisherProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiPublisherProfileDTO.class);
        ApiPublisherProfileDTO apiPublisherProfileDTO1 = new ApiPublisherProfileDTO();
        apiPublisherProfileDTO1.setId(1L);
        ApiPublisherProfileDTO apiPublisherProfileDTO2 = new ApiPublisherProfileDTO();
        assertThat(apiPublisherProfileDTO1).isNotEqualTo(apiPublisherProfileDTO2);
        apiPublisherProfileDTO2.setId(apiPublisherProfileDTO1.getId());
        assertThat(apiPublisherProfileDTO1).isEqualTo(apiPublisherProfileDTO2);
        apiPublisherProfileDTO2.setId(2L);
        assertThat(apiPublisherProfileDTO1).isNotEqualTo(apiPublisherProfileDTO2);
        apiPublisherProfileDTO1.setId(null);
        assertThat(apiPublisherProfileDTO1).isNotEqualTo(apiPublisherProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiPublisherProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiPublisherProfileMapper.fromId(null)).isNull();
    }
}

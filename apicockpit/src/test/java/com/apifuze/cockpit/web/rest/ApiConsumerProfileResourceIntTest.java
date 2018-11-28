package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiConsumerProfile;
import com.apifuze.cockpit.repository.ApiConsumerProfileRepository;
import com.apifuze.cockpit.service.ApiConsumerProfileService;
import com.apifuze.cockpit.service.dto.ApiConsumerProfileDTO;
import com.apifuze.cockpit.service.mapper.ApiConsumerProfileMapper;
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
 * Test class for the ApiConsumerProfileResource REST controller.
 *
 * @see ApiConsumerProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiConsumerProfileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApiConsumerProfileRepository apiConsumerProfileRepository;

    @Autowired
    private ApiConsumerProfileMapper apiConsumerProfileMapper;

    @Autowired
    private ApiConsumerProfileService apiConsumerProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiConsumerProfileMockMvc;

    private ApiConsumerProfile apiConsumerProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiConsumerProfileResource apiConsumerProfileResource = new ApiConsumerProfileResource(apiConsumerProfileService);
        this.restApiConsumerProfileMockMvc = MockMvcBuilders.standaloneSetup(apiConsumerProfileResource)
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
    public static ApiConsumerProfile createEntity(EntityManager em) {
        ApiConsumerProfile apiConsumerProfile = new ApiConsumerProfile()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .dateCreated(DEFAULT_DATE_CREATED);
        return apiConsumerProfile;
    }

    @Before
    public void initTest() {
        apiConsumerProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiConsumerProfile() throws Exception {
        int databaseSizeBeforeCreate = apiConsumerProfileRepository.findAll().size();

        // Create the ApiConsumerProfile
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(apiConsumerProfile);
        restApiConsumerProfileMockMvc.perform(post("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiConsumerProfile in the database
        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ApiConsumerProfile testApiConsumerProfile = apiConsumerProfileList.get(apiConsumerProfileList.size() - 1);
        assertThat(testApiConsumerProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiConsumerProfile.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiConsumerProfile.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createApiConsumerProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiConsumerProfileRepository.findAll().size();

        // Create the ApiConsumerProfile with an existing ID
        apiConsumerProfile.setId(1L);
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(apiConsumerProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiConsumerProfileMockMvc.perform(post("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiConsumerProfile in the database
        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiConsumerProfileRepository.findAll().size();
        // set the field null
        apiConsumerProfile.setName(null);

        // Create the ApiConsumerProfile, which fails.
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(apiConsumerProfile);

        restApiConsumerProfileMockMvc.perform(post("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiConsumerProfileRepository.findAll().size();
        // set the field null
        apiConsumerProfile.setDateCreated(null);

        // Create the ApiConsumerProfile, which fails.
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(apiConsumerProfile);

        restApiConsumerProfileMockMvc.perform(post("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isBadRequest());

        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiConsumerProfiles() throws Exception {
        // Initialize the database
        apiConsumerProfileRepository.saveAndFlush(apiConsumerProfile);

        // Get all the apiConsumerProfileList
        restApiConsumerProfileMockMvc.perform(get("/api/api-consumer-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiConsumerProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @Test
    @Transactional
    public void getApiConsumerProfile() throws Exception {
        // Initialize the database
        apiConsumerProfileRepository.saveAndFlush(apiConsumerProfile);

        // Get the apiConsumerProfile
        restApiConsumerProfileMockMvc.perform(get("/api/api-consumer-profiles/{id}", apiConsumerProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiConsumerProfile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiConsumerProfile() throws Exception {
        // Get the apiConsumerProfile
        restApiConsumerProfileMockMvc.perform(get("/api/api-consumer-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiConsumerProfile() throws Exception {
        // Initialize the database
        apiConsumerProfileRepository.saveAndFlush(apiConsumerProfile);

        int databaseSizeBeforeUpdate = apiConsumerProfileRepository.findAll().size();

        // Update the apiConsumerProfile
        ApiConsumerProfile updatedApiConsumerProfile = apiConsumerProfileRepository.findById(apiConsumerProfile.getId()).get();
        // Disconnect from session so that the updates on updatedApiConsumerProfile are not directly saved in db
        em.detach(updatedApiConsumerProfile);
        updatedApiConsumerProfile
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .dateCreated(UPDATED_DATE_CREATED);
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(updatedApiConsumerProfile);

        restApiConsumerProfileMockMvc.perform(put("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isOk());

        // Validate the ApiConsumerProfile in the database
        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeUpdate);
        ApiConsumerProfile testApiConsumerProfile = apiConsumerProfileList.get(apiConsumerProfileList.size() - 1);
        assertThat(testApiConsumerProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiConsumerProfile.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiConsumerProfile.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingApiConsumerProfile() throws Exception {
        int databaseSizeBeforeUpdate = apiConsumerProfileRepository.findAll().size();

        // Create the ApiConsumerProfile
        ApiConsumerProfileDTO apiConsumerProfileDTO = apiConsumerProfileMapper.toDto(apiConsumerProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiConsumerProfileMockMvc.perform(put("/api/api-consumer-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiConsumerProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiConsumerProfile in the database
        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiConsumerProfile() throws Exception {
        // Initialize the database
        apiConsumerProfileRepository.saveAndFlush(apiConsumerProfile);

        int databaseSizeBeforeDelete = apiConsumerProfileRepository.findAll().size();

        // Get the apiConsumerProfile
        restApiConsumerProfileMockMvc.perform(delete("/api/api-consumer-profiles/{id}", apiConsumerProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiConsumerProfile> apiConsumerProfileList = apiConsumerProfileRepository.findAll();
        assertThat(apiConsumerProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiConsumerProfile.class);
        ApiConsumerProfile apiConsumerProfile1 = new ApiConsumerProfile();
        apiConsumerProfile1.setId(1L);
        ApiConsumerProfile apiConsumerProfile2 = new ApiConsumerProfile();
        apiConsumerProfile2.setId(apiConsumerProfile1.getId());
        assertThat(apiConsumerProfile1).isEqualTo(apiConsumerProfile2);
        apiConsumerProfile2.setId(2L);
        assertThat(apiConsumerProfile1).isNotEqualTo(apiConsumerProfile2);
        apiConsumerProfile1.setId(null);
        assertThat(apiConsumerProfile1).isNotEqualTo(apiConsumerProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiConsumerProfileDTO.class);
        ApiConsumerProfileDTO apiConsumerProfileDTO1 = new ApiConsumerProfileDTO();
        apiConsumerProfileDTO1.setId(1L);
        ApiConsumerProfileDTO apiConsumerProfileDTO2 = new ApiConsumerProfileDTO();
        assertThat(apiConsumerProfileDTO1).isNotEqualTo(apiConsumerProfileDTO2);
        apiConsumerProfileDTO2.setId(apiConsumerProfileDTO1.getId());
        assertThat(apiConsumerProfileDTO1).isEqualTo(apiConsumerProfileDTO2);
        apiConsumerProfileDTO2.setId(2L);
        assertThat(apiConsumerProfileDTO1).isNotEqualTo(apiConsumerProfileDTO2);
        apiConsumerProfileDTO1.setId(null);
        assertThat(apiConsumerProfileDTO1).isNotEqualTo(apiConsumerProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiConsumerProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiConsumerProfileMapper.fromId(null)).isNull();
    }
}

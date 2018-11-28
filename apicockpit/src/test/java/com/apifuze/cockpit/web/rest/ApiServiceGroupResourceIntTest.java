package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.ApiServiceGroup;
import com.apifuze.cockpit.repository.ApiServiceGroupRepository;
import com.apifuze.cockpit.service.ApiServiceGroupService;
import com.apifuze.cockpit.service.dto.ApiServiceGroupDTO;
import com.apifuze.cockpit.service.mapper.ApiServiceGroupMapper;
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
import java.util.List;


import static com.apifuze.cockpit.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApiServiceGroupResource REST controller.
 *
 * @see ApiServiceGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class ApiServiceGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ApiServiceGroupRepository apiServiceGroupRepository;

    @Autowired
    private ApiServiceGroupMapper apiServiceGroupMapper;

    @Autowired
    private ApiServiceGroupService apiServiceGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiServiceGroupMockMvc;

    private ApiServiceGroup apiServiceGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiServiceGroupResource apiServiceGroupResource = new ApiServiceGroupResource(apiServiceGroupService);
        this.restApiServiceGroupMockMvc = MockMvcBuilders.standaloneSetup(apiServiceGroupResource)
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
    public static ApiServiceGroup createEntity(EntityManager em) {
        ApiServiceGroup apiServiceGroup = new ApiServiceGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return apiServiceGroup;
    }

    @Before
    public void initTest() {
        apiServiceGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiServiceGroup() throws Exception {
        int databaseSizeBeforeCreate = apiServiceGroupRepository.findAll().size();

        // Create the ApiServiceGroup
        ApiServiceGroupDTO apiServiceGroupDTO = apiServiceGroupMapper.toDto(apiServiceGroup);
        restApiServiceGroupMockMvc.perform(post("/api/api-service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the ApiServiceGroup in the database
        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ApiServiceGroup testApiServiceGroup = apiServiceGroupList.get(apiServiceGroupList.size() - 1);
        assertThat(testApiServiceGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiServiceGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createApiServiceGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiServiceGroupRepository.findAll().size();

        // Create the ApiServiceGroup with an existing ID
        apiServiceGroup.setId(1L);
        ApiServiceGroupDTO apiServiceGroupDTO = apiServiceGroupMapper.toDto(apiServiceGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiServiceGroupMockMvc.perform(post("/api/api-service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceGroup in the database
        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiServiceGroupRepository.findAll().size();
        // set the field null
        apiServiceGroup.setName(null);

        // Create the ApiServiceGroup, which fails.
        ApiServiceGroupDTO apiServiceGroupDTO = apiServiceGroupMapper.toDto(apiServiceGroup);

        restApiServiceGroupMockMvc.perform(post("/api/api-service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceGroupDTO)))
            .andExpect(status().isBadRequest());

        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiServiceGroups() throws Exception {
        // Initialize the database
        apiServiceGroupRepository.saveAndFlush(apiServiceGroup);

        // Get all the apiServiceGroupList
        restApiServiceGroupMockMvc.perform(get("/api/api-service-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiServiceGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getApiServiceGroup() throws Exception {
        // Initialize the database
        apiServiceGroupRepository.saveAndFlush(apiServiceGroup);

        // Get the apiServiceGroup
        restApiServiceGroupMockMvc.perform(get("/api/api-service-groups/{id}", apiServiceGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiServiceGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApiServiceGroup() throws Exception {
        // Get the apiServiceGroup
        restApiServiceGroupMockMvc.perform(get("/api/api-service-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiServiceGroup() throws Exception {
        // Initialize the database
        apiServiceGroupRepository.saveAndFlush(apiServiceGroup);

        int databaseSizeBeforeUpdate = apiServiceGroupRepository.findAll().size();

        // Update the apiServiceGroup
        ApiServiceGroup updatedApiServiceGroup = apiServiceGroupRepository.findById(apiServiceGroup.getId()).get();
        // Disconnect from session so that the updates on updatedApiServiceGroup are not directly saved in db
        em.detach(updatedApiServiceGroup);
        updatedApiServiceGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ApiServiceGroupDTO apiServiceGroupDTO = apiServiceGroupMapper.toDto(updatedApiServiceGroup);

        restApiServiceGroupMockMvc.perform(put("/api/api-service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceGroupDTO)))
            .andExpect(status().isOk());

        // Validate the ApiServiceGroup in the database
        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeUpdate);
        ApiServiceGroup testApiServiceGroup = apiServiceGroupList.get(apiServiceGroupList.size() - 1);
        assertThat(testApiServiceGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiServiceGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingApiServiceGroup() throws Exception {
        int databaseSizeBeforeUpdate = apiServiceGroupRepository.findAll().size();

        // Create the ApiServiceGroup
        ApiServiceGroupDTO apiServiceGroupDTO = apiServiceGroupMapper.toDto(apiServiceGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiServiceGroupMockMvc.perform(put("/api/api-service-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiServiceGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApiServiceGroup in the database
        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiServiceGroup() throws Exception {
        // Initialize the database
        apiServiceGroupRepository.saveAndFlush(apiServiceGroup);

        int databaseSizeBeforeDelete = apiServiceGroupRepository.findAll().size();

        // Get the apiServiceGroup
        restApiServiceGroupMockMvc.perform(delete("/api/api-service-groups/{id}", apiServiceGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiServiceGroup> apiServiceGroupList = apiServiceGroupRepository.findAll();
        assertThat(apiServiceGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiServiceGroup.class);
        ApiServiceGroup apiServiceGroup1 = new ApiServiceGroup();
        apiServiceGroup1.setId(1L);
        ApiServiceGroup apiServiceGroup2 = new ApiServiceGroup();
        apiServiceGroup2.setId(apiServiceGroup1.getId());
        assertThat(apiServiceGroup1).isEqualTo(apiServiceGroup2);
        apiServiceGroup2.setId(2L);
        assertThat(apiServiceGroup1).isNotEqualTo(apiServiceGroup2);
        apiServiceGroup1.setId(null);
        assertThat(apiServiceGroup1).isNotEqualTo(apiServiceGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiServiceGroupDTO.class);
        ApiServiceGroupDTO apiServiceGroupDTO1 = new ApiServiceGroupDTO();
        apiServiceGroupDTO1.setId(1L);
        ApiServiceGroupDTO apiServiceGroupDTO2 = new ApiServiceGroupDTO();
        assertThat(apiServiceGroupDTO1).isNotEqualTo(apiServiceGroupDTO2);
        apiServiceGroupDTO2.setId(apiServiceGroupDTO1.getId());
        assertThat(apiServiceGroupDTO1).isEqualTo(apiServiceGroupDTO2);
        apiServiceGroupDTO2.setId(2L);
        assertThat(apiServiceGroupDTO1).isNotEqualTo(apiServiceGroupDTO2);
        apiServiceGroupDTO1.setId(null);
        assertThat(apiServiceGroupDTO1).isNotEqualTo(apiServiceGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiServiceGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiServiceGroupMapper.fromId(null)).isNull();
    }
}

package com.apifuze.cockpit.web.rest;

import com.apifuze.cockpit.ApicockpitApp;

import com.apifuze.cockpit.domain.PlatformUser;
import com.apifuze.cockpit.repository.PlatformUserRepository;
import com.apifuze.cockpit.service.PlatformUserService;
import com.apifuze.cockpit.service.dto.PlatformUserDTO;
import com.apifuze.cockpit.service.mapper.PlatformUserMapper;
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
 * Test class for the PlatformUserResource REST controller.
 *
 * @see PlatformUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApicockpitApp.class)
public class PlatformUserResourceIntTest {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Autowired
    private PlatformUserMapper platformUserMapper;

    @Autowired
    private PlatformUserService platformUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlatformUserMockMvc;

    private PlatformUser platformUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlatformUserResource platformUserResource = new PlatformUserResource(platformUserService);
        this.restPlatformUserMockMvc = MockMvcBuilders.standaloneSetup(platformUserResource)
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
    public static PlatformUser createEntity(EntityManager em) {
        PlatformUser platformUser = new PlatformUser()
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return platformUser;
    }

    @Before
    public void initTest() {
        platformUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlatformUser() throws Exception {
        int databaseSizeBeforeCreate = platformUserRepository.findAll().size();

        // Create the PlatformUser
        PlatformUserDTO platformUserDTO = platformUserMapper.toDto(platformUser);
        restPlatformUserMockMvc.perform(post("/api/platform-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(platformUserDTO)))
            .andExpect(status().isCreated());

        // Validate the PlatformUser in the database
        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeCreate + 1);
        PlatformUser testPlatformUser = platformUserList.get(platformUserList.size() - 1);
        assertThat(testPlatformUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createPlatformUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = platformUserRepository.findAll().size();

        // Create the PlatformUser with an existing ID
        platformUser.setId(1L);
        PlatformUserDTO platformUserDTO = platformUserMapper.toDto(platformUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlatformUserMockMvc.perform(post("/api/platform-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(platformUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlatformUser in the database
        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = platformUserRepository.findAll().size();
        // set the field null
        platformUser.setPhoneNumber(null);

        // Create the PlatformUser, which fails.
        PlatformUserDTO platformUserDTO = platformUserMapper.toDto(platformUser);

        restPlatformUserMockMvc.perform(post("/api/platform-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(platformUserDTO)))
            .andExpect(status().isBadRequest());

        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlatformUsers() throws Exception {
        // Initialize the database
        platformUserRepository.saveAndFlush(platformUser);

        // Get all the platformUserList
        restPlatformUserMockMvc.perform(get("/api/platform-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(platformUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getPlatformUser() throws Exception {
        // Initialize the database
        platformUserRepository.saveAndFlush(platformUser);

        // Get the platformUser
        restPlatformUserMockMvc.perform(get("/api/platform-users/{id}", platformUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(platformUser.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlatformUser() throws Exception {
        // Get the platformUser
        restPlatformUserMockMvc.perform(get("/api/platform-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlatformUser() throws Exception {
        // Initialize the database
        platformUserRepository.saveAndFlush(platformUser);

        int databaseSizeBeforeUpdate = platformUserRepository.findAll().size();

        // Update the platformUser
        PlatformUser updatedPlatformUser = platformUserRepository.findById(platformUser.getId()).get();
        // Disconnect from session so that the updates on updatedPlatformUser are not directly saved in db
        em.detach(updatedPlatformUser);
        updatedPlatformUser
            .phoneNumber(UPDATED_PHONE_NUMBER);
        PlatformUserDTO platformUserDTO = platformUserMapper.toDto(updatedPlatformUser);

        restPlatformUserMockMvc.perform(put("/api/platform-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(platformUserDTO)))
            .andExpect(status().isOk());

        // Validate the PlatformUser in the database
        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeUpdate);
        PlatformUser testPlatformUser = platformUserList.get(platformUserList.size() - 1);
        assertThat(testPlatformUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingPlatformUser() throws Exception {
        int databaseSizeBeforeUpdate = platformUserRepository.findAll().size();

        // Create the PlatformUser
        PlatformUserDTO platformUserDTO = platformUserMapper.toDto(platformUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlatformUserMockMvc.perform(put("/api/platform-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(platformUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlatformUser in the database
        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlatformUser() throws Exception {
        // Initialize the database
        platformUserRepository.saveAndFlush(platformUser);

        int databaseSizeBeforeDelete = platformUserRepository.findAll().size();

        // Get the platformUser
        restPlatformUserMockMvc.perform(delete("/api/platform-users/{id}", platformUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PlatformUser> platformUserList = platformUserRepository.findAll();
        assertThat(platformUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlatformUser.class);
        PlatformUser platformUser1 = new PlatformUser();
        platformUser1.setId(1L);
        PlatformUser platformUser2 = new PlatformUser();
        platformUser2.setId(platformUser1.getId());
        assertThat(platformUser1).isEqualTo(platformUser2);
        platformUser2.setId(2L);
        assertThat(platformUser1).isNotEqualTo(platformUser2);
        platformUser1.setId(null);
        assertThat(platformUser1).isNotEqualTo(platformUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlatformUserDTO.class);
        PlatformUserDTO platformUserDTO1 = new PlatformUserDTO();
        platformUserDTO1.setId(1L);
        PlatformUserDTO platformUserDTO2 = new PlatformUserDTO();
        assertThat(platformUserDTO1).isNotEqualTo(platformUserDTO2);
        platformUserDTO2.setId(platformUserDTO1.getId());
        assertThat(platformUserDTO1).isEqualTo(platformUserDTO2);
        platformUserDTO2.setId(2L);
        assertThat(platformUserDTO1).isNotEqualTo(platformUserDTO2);
        platformUserDTO1.setId(null);
        assertThat(platformUserDTO1).isNotEqualTo(platformUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(platformUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(platformUserMapper.fromId(null)).isNull();
    }
}

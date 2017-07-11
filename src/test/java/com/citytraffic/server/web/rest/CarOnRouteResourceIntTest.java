package com.citytraffic.server.web.rest;

import com.citytraffic.server.CityTrafficServerApp;

import com.citytraffic.server.domain.CarOnRoute;
import com.citytraffic.server.repository.CarOnRouteRepository;
import com.citytraffic.server.service.CarOnRouteService;
import com.citytraffic.server.service.dto.CarOnRouteDTO;
import com.citytraffic.server.service.mapper.CarOnRouteMapper;
import com.citytraffic.server.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CarOnRouteResource REST controller.
 *
 * @see CarOnRouteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityTrafficServerApp.class)
public class CarOnRouteResourceIntTest {

    private static final LocalDate DEFAULT_TIME_LOGIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME_LOGIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TIME_LOGOUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIME_LOGOUT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CarOnRouteRepository carOnRouteRepository;

    @Autowired
    private CarOnRouteMapper carOnRouteMapper;

    @Autowired
    private CarOnRouteService carOnRouteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCarOnRouteMockMvc;

    private CarOnRoute carOnRoute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarOnRouteResource carOnRouteResource = new CarOnRouteResource(carOnRouteService);
        this.restCarOnRouteMockMvc = MockMvcBuilders.standaloneSetup(carOnRouteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarOnRoute createEntity(EntityManager em) {
        CarOnRoute carOnRoute = new CarOnRoute()
            .timeLogin(DEFAULT_TIME_LOGIN)
            .timeLogout(DEFAULT_TIME_LOGOUT);
        return carOnRoute;
    }

    @Before
    public void initTest() {
        carOnRoute = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarOnRoute() throws Exception {
        int databaseSizeBeforeCreate = carOnRouteRepository.findAll().size();

        // Create the CarOnRoute
        CarOnRouteDTO carOnRouteDTO = carOnRouteMapper.toDto(carOnRoute);
        restCarOnRouteMockMvc.perform(post("/api/car-on-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carOnRouteDTO)))
            .andExpect(status().isCreated());

        // Validate the CarOnRoute in the database
        List<CarOnRoute> carOnRouteList = carOnRouteRepository.findAll();
        assertThat(carOnRouteList).hasSize(databaseSizeBeforeCreate + 1);
        CarOnRoute testCarOnRoute = carOnRouteList.get(carOnRouteList.size() - 1);
        assertThat(testCarOnRoute.getTimeLogin()).isEqualTo(DEFAULT_TIME_LOGIN);
        assertThat(testCarOnRoute.getTimeLogout()).isEqualTo(DEFAULT_TIME_LOGOUT);
    }

    @Test
    @Transactional
    public void createCarOnRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carOnRouteRepository.findAll().size();

        // Create the CarOnRoute with an existing ID
        carOnRoute.setId(1L);
        CarOnRouteDTO carOnRouteDTO = carOnRouteMapper.toDto(carOnRoute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarOnRouteMockMvc.perform(post("/api/car-on-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carOnRouteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CarOnRoute> carOnRouteList = carOnRouteRepository.findAll();
        assertThat(carOnRouteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCarOnRoutes() throws Exception {
        // Initialize the database
        carOnRouteRepository.saveAndFlush(carOnRoute);

        // Get all the carOnRouteList
        restCarOnRouteMockMvc.perform(get("/api/car-on-routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carOnRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeLogin").value(hasItem(DEFAULT_TIME_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].timeLogout").value(hasItem(DEFAULT_TIME_LOGOUT.toString())));
    }

    @Test
    @Transactional
    public void getCarOnRoute() throws Exception {
        // Initialize the database
        carOnRouteRepository.saveAndFlush(carOnRoute);

        // Get the carOnRoute
        restCarOnRouteMockMvc.perform(get("/api/car-on-routes/{id}", carOnRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(carOnRoute.getId().intValue()))
            .andExpect(jsonPath("$.timeLogin").value(DEFAULT_TIME_LOGIN.toString()))
            .andExpect(jsonPath("$.timeLogout").value(DEFAULT_TIME_LOGOUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCarOnRoute() throws Exception {
        // Get the carOnRoute
        restCarOnRouteMockMvc.perform(get("/api/car-on-routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarOnRoute() throws Exception {
        // Initialize the database
        carOnRouteRepository.saveAndFlush(carOnRoute);
        int databaseSizeBeforeUpdate = carOnRouteRepository.findAll().size();

        // Update the carOnRoute
        CarOnRoute updatedCarOnRoute = carOnRouteRepository.findOne(carOnRoute.getId());
        updatedCarOnRoute
            .timeLogin(UPDATED_TIME_LOGIN)
            .timeLogout(UPDATED_TIME_LOGOUT);
        CarOnRouteDTO carOnRouteDTO = carOnRouteMapper.toDto(updatedCarOnRoute);

        restCarOnRouteMockMvc.perform(put("/api/car-on-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carOnRouteDTO)))
            .andExpect(status().isOk());

        // Validate the CarOnRoute in the database
        List<CarOnRoute> carOnRouteList = carOnRouteRepository.findAll();
        assertThat(carOnRouteList).hasSize(databaseSizeBeforeUpdate);
        CarOnRoute testCarOnRoute = carOnRouteList.get(carOnRouteList.size() - 1);
        assertThat(testCarOnRoute.getTimeLogin()).isEqualTo(UPDATED_TIME_LOGIN);
        assertThat(testCarOnRoute.getTimeLogout()).isEqualTo(UPDATED_TIME_LOGOUT);
    }

    @Test
    @Transactional
    public void updateNonExistingCarOnRoute() throws Exception {
        int databaseSizeBeforeUpdate = carOnRouteRepository.findAll().size();

        // Create the CarOnRoute
        CarOnRouteDTO carOnRouteDTO = carOnRouteMapper.toDto(carOnRoute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCarOnRouteMockMvc.perform(put("/api/car-on-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(carOnRouteDTO)))
            .andExpect(status().isCreated());

        // Validate the CarOnRoute in the database
        List<CarOnRoute> carOnRouteList = carOnRouteRepository.findAll();
        assertThat(carOnRouteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCarOnRoute() throws Exception {
        // Initialize the database
        carOnRouteRepository.saveAndFlush(carOnRoute);
        int databaseSizeBeforeDelete = carOnRouteRepository.findAll().size();

        // Get the carOnRoute
        restCarOnRouteMockMvc.perform(delete("/api/car-on-routes/{id}", carOnRoute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CarOnRoute> carOnRouteList = carOnRouteRepository.findAll();
        assertThat(carOnRouteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarOnRoute.class);
        CarOnRoute carOnRoute1 = new CarOnRoute();
        carOnRoute1.setId(1L);
        CarOnRoute carOnRoute2 = new CarOnRoute();
        carOnRoute2.setId(carOnRoute1.getId());
        assertThat(carOnRoute1).isEqualTo(carOnRoute2);
        carOnRoute2.setId(2L);
        assertThat(carOnRoute1).isNotEqualTo(carOnRoute2);
        carOnRoute1.setId(null);
        assertThat(carOnRoute1).isNotEqualTo(carOnRoute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarOnRouteDTO.class);
        CarOnRouteDTO carOnRouteDTO1 = new CarOnRouteDTO();
        carOnRouteDTO1.setId(1L);
        CarOnRouteDTO carOnRouteDTO2 = new CarOnRouteDTO();
        assertThat(carOnRouteDTO1).isNotEqualTo(carOnRouteDTO2);
        carOnRouteDTO2.setId(carOnRouteDTO1.getId());
        assertThat(carOnRouteDTO1).isEqualTo(carOnRouteDTO2);
        carOnRouteDTO2.setId(2L);
        assertThat(carOnRouteDTO1).isNotEqualTo(carOnRouteDTO2);
        carOnRouteDTO1.setId(null);
        assertThat(carOnRouteDTO1).isNotEqualTo(carOnRouteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(carOnRouteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(carOnRouteMapper.fromId(null)).isNull();
    }
}

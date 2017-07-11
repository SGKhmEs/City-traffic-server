package com.citytraffic.server.web.rest;

import com.citytraffic.server.CityTrafficServerApp;

import com.citytraffic.server.domain.BusRoute;
import com.citytraffic.server.repository.BusRouteRepository;
import com.citytraffic.server.service.BusRouteService;
import com.citytraffic.server.service.dto.BusRouteDTO;
import com.citytraffic.server.service.mapper.BusRouteMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusRouteResource REST controller.
 *
 * @see BusRouteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CityTrafficServerApp.class)
public class BusRouteResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BusRouteRepository busRouteRepository;

    @Autowired
    private BusRouteMapper busRouteMapper;

    @Autowired
    private BusRouteService busRouteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusRouteMockMvc;

    private BusRoute busRoute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusRouteResource busRouteResource = new BusRouteResource(busRouteService);
        this.restBusRouteMockMvc = MockMvcBuilders.standaloneSetup(busRouteResource)
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
    public static BusRoute createEntity(EntityManager em) {
        BusRoute busRoute = new BusRoute()
            .name(DEFAULT_NAME);
        return busRoute;
    }

    @Before
    public void initTest() {
        busRoute = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusRoute() throws Exception {
        int databaseSizeBeforeCreate = busRouteRepository.findAll().size();

        // Create the BusRoute
        BusRouteDTO busRouteDTO = busRouteMapper.toDto(busRoute);
        restBusRouteMockMvc.perform(post("/api/bus-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(busRouteDTO)))
            .andExpect(status().isCreated());

        // Validate the BusRoute in the database
        List<BusRoute> busRouteList = busRouteRepository.findAll();
        assertThat(busRouteList).hasSize(databaseSizeBeforeCreate + 1);
        BusRoute testBusRoute = busRouteList.get(busRouteList.size() - 1);
        assertThat(testBusRoute.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBusRouteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = busRouteRepository.findAll().size();

        // Create the BusRoute with an existing ID
        busRoute.setId(1L);
        BusRouteDTO busRouteDTO = busRouteMapper.toDto(busRoute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusRouteMockMvc.perform(post("/api/bus-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(busRouteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BusRoute> busRouteList = busRouteRepository.findAll();
        assertThat(busRouteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusRoutes() throws Exception {
        // Initialize the database
        busRouteRepository.saveAndFlush(busRoute);

        // Get all the busRouteList
        restBusRouteMockMvc.perform(get("/api/bus-routes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(busRoute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBusRoute() throws Exception {
        // Initialize the database
        busRouteRepository.saveAndFlush(busRoute);

        // Get the busRoute
        restBusRouteMockMvc.perform(get("/api/bus-routes/{id}", busRoute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(busRoute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusRoute() throws Exception {
        // Get the busRoute
        restBusRouteMockMvc.perform(get("/api/bus-routes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusRoute() throws Exception {
        // Initialize the database
        busRouteRepository.saveAndFlush(busRoute);
        int databaseSizeBeforeUpdate = busRouteRepository.findAll().size();

        // Update the busRoute
        BusRoute updatedBusRoute = busRouteRepository.findOne(busRoute.getId());
        updatedBusRoute
            .name(UPDATED_NAME);
        BusRouteDTO busRouteDTO = busRouteMapper.toDto(updatedBusRoute);

        restBusRouteMockMvc.perform(put("/api/bus-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(busRouteDTO)))
            .andExpect(status().isOk());

        // Validate the BusRoute in the database
        List<BusRoute> busRouteList = busRouteRepository.findAll();
        assertThat(busRouteList).hasSize(databaseSizeBeforeUpdate);
        BusRoute testBusRoute = busRouteList.get(busRouteList.size() - 1);
        assertThat(testBusRoute.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBusRoute() throws Exception {
        int databaseSizeBeforeUpdate = busRouteRepository.findAll().size();

        // Create the BusRoute
        BusRouteDTO busRouteDTO = busRouteMapper.toDto(busRoute);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusRouteMockMvc.perform(put("/api/bus-routes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(busRouteDTO)))
            .andExpect(status().isCreated());

        // Validate the BusRoute in the database
        List<BusRoute> busRouteList = busRouteRepository.findAll();
        assertThat(busRouteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusRoute() throws Exception {
        // Initialize the database
        busRouteRepository.saveAndFlush(busRoute);
        int databaseSizeBeforeDelete = busRouteRepository.findAll().size();

        // Get the busRoute
        restBusRouteMockMvc.perform(delete("/api/bus-routes/{id}", busRoute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusRoute> busRouteList = busRouteRepository.findAll();
        assertThat(busRouteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusRoute.class);
        BusRoute busRoute1 = new BusRoute();
        busRoute1.setId(1L);
        BusRoute busRoute2 = new BusRoute();
        busRoute2.setId(busRoute1.getId());
        assertThat(busRoute1).isEqualTo(busRoute2);
        busRoute2.setId(2L);
        assertThat(busRoute1).isNotEqualTo(busRoute2);
        busRoute1.setId(null);
        assertThat(busRoute1).isNotEqualTo(busRoute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusRouteDTO.class);
        BusRouteDTO busRouteDTO1 = new BusRouteDTO();
        busRouteDTO1.setId(1L);
        BusRouteDTO busRouteDTO2 = new BusRouteDTO();
        assertThat(busRouteDTO1).isNotEqualTo(busRouteDTO2);
        busRouteDTO2.setId(busRouteDTO1.getId());
        assertThat(busRouteDTO1).isEqualTo(busRouteDTO2);
        busRouteDTO2.setId(2L);
        assertThat(busRouteDTO1).isNotEqualTo(busRouteDTO2);
        busRouteDTO1.setId(null);
        assertThat(busRouteDTO1).isNotEqualTo(busRouteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(busRouteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(busRouteMapper.fromId(null)).isNull();
    }
}

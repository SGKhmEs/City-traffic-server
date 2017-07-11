import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CityTrafficServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CarOnRouteDetailComponent } from '../../../../../../main/webapp/app/entities/car-on-route/car-on-route-detail.component';
import { CarOnRouteService } from '../../../../../../main/webapp/app/entities/car-on-route/car-on-route.service';
import { CarOnRoute } from '../../../../../../main/webapp/app/entities/car-on-route/car-on-route.model';

describe('Component Tests', () => {

    describe('CarOnRoute Management Detail Component', () => {
        let comp: CarOnRouteDetailComponent;
        let fixture: ComponentFixture<CarOnRouteDetailComponent>;
        let service: CarOnRouteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CityTrafficServerTestModule],
                declarations: [CarOnRouteDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CarOnRouteService,
                    JhiEventManager
                ]
            }).overrideTemplate(CarOnRouteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CarOnRouteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarOnRouteService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CarOnRoute(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.carOnRoute).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

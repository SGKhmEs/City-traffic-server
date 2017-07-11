import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { CityTrafficServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GPSDetailComponent } from '../../../../../../main/webapp/app/entities/g-ps/gps-detail.component';
import { GPSService } from '../../../../../../main/webapp/app/entities/g-ps/gps.service';
import { GPS } from '../../../../../../main/webapp/app/entities/g-ps/gps.model';

describe('Component Tests', () => {

    describe('GPS Management Detail Component', () => {
        let comp: GPSDetailComponent;
        let fixture: ComponentFixture<GPSDetailComponent>;
        let service: GPSService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CityTrafficServerTestModule],
                declarations: [GPSDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    GPSService,
                    JhiEventManager
                ]
            }).overrideTemplate(GPSDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GPSDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GPSService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GPS(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.gPS).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { CityTrafficServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CabPositionDetailComponent } from '../../../../../../main/webapp/app/entities/cab-position/cab-position-detail.component';
import { CabPositionService } from '../../../../../../main/webapp/app/entities/cab-position/cab-position.service';
import { CabPosition } from '../../../../../../main/webapp/app/entities/cab-position/cab-position.model';

describe('Component Tests', () => {

    describe('CabPosition Management Detail Component', () => {
        let comp: CabPositionDetailComponent;
        let fixture: ComponentFixture<CabPositionDetailComponent>;
        let service: CabPositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CityTrafficServerTestModule],
                declarations: [CabPositionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CabPositionService,
                    EventManager
                ]
            }).overrideComponent(CabPositionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CabPositionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CabPositionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CabPosition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cabPosition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApicockpitTestModule } from '../../../test.module';
import { PlatformUserComponent } from 'app/entities/platform-user/platform-user.component';
import { PlatformUserService } from 'app/entities/platform-user/platform-user.service';
import { PlatformUser } from 'app/shared/model/platform-user.model';

describe('Component Tests', () => {
    describe('PlatformUser Management Component', () => {
        let comp: PlatformUserComponent;
        let fixture: ComponentFixture<PlatformUserComponent>;
        let service: PlatformUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ApicockpitTestModule],
                declarations: [PlatformUserComponent],
                providers: []
            })
                .overrideTemplate(PlatformUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlatformUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlatformUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PlatformUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.platformUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

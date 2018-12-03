import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlatformUser } from 'app/shared/model/platform-user.model';

@Component({
    selector: 'jhi-platform-user-detail',
    templateUrl: './platform-user-detail.component.html'
})
export class PlatformUserDetailComponent implements OnInit {
    platformUser: IPlatformUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ platformUser }) => {
            this.platformUser = platformUser;
        });
    }

    previousState() {
        window.history.back();
    }
}

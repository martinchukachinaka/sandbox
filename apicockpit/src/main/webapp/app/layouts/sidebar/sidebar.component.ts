import { Component, OnInit } from '@angular/core';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
    constructor(private principal: Principal) {}

    ngOnInit() {}

    isAuthenticated() {
        console.log('user is authenticated ', this.principal.isAuthenticated());
        return this.principal.isAuthenticated();
    }
}

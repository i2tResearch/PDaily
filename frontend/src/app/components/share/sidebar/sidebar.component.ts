import {Component, ViewChild} from '@angular/core';

import { RadioButton } from '@syncfusion/ej2-buttons';
import { Sidebar } from '@syncfusion/ej2-navigations';
import { enableRipple } from '@syncfusion/ej2-base';
import {SidebarComponent} from '@syncfusion/ej2-angular-navigations';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarPDaylyComponent {

  @ViewChild('sidebar') sidebar: SidebarComponent;
  openSideBar: boolean;

  constructor() {
    this.openSideBar = true;
  }

  showSideBar() {
    console.log("en click");
    if (this.openSideBar) {
      this.sidebar.hide();
    } else {
      this.sidebar.show();
    }
    this.openSideBar = !this.openSideBar;
  }

  public onCreated(args: any) {
    this.sidebar.element.style.visibility = '';
  }



  // defined the array of data
  public hierarchicalData: any[] = [
    { id: '01', name: 'Home', expanded: true, iconCss: 'fas fa-home'
    },
    {
      id: '02', name: 'Pacientes', iconCss: 'fas fa-users',
      subChild: [
        {id: '02-01', name: 'Agregar paciente', iconCss: 'fas fa-user-plus'},
        {id: '02-02', name: 'Buscar paciente', iconCss: 'fas fa-search'},
        {id: '02-03', name: 'Lista de pacientes', iconCss: 'fas fa-users'}
      ]
    },
    {
      id: '03', name: 'Configuracion', iconCss: 'fas fa-tools',
      subChild: [
        {id: '03-01', name: 'Configurar perfil', iconCss: 'fas fa-user-cog'},
        {id: '03-02', name: 'Clinicas asociadas', iconCss: 'fas fa-hospital-alt'},
      ]
    }
  ];
  public field:any ={ dataSource: this.hierarchicalData, id: 'id', text: 'name', child: 'subChild' };
}

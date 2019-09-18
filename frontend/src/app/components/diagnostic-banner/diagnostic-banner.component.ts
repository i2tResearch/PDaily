import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-diagnostic-banner',
  templateUrl: './diagnostic-banner.component.html',
  styleUrls: ['./diagnostic-banner.component.css']
})
export class DiagnosticBannerComponent {

  events: any[] = [
    { label: 'Comidas',
      color: '#C9B37C',
      icon: 'assets/comida.png',
      active: false
    },
    { label: 'Levodopa',
      color: '#009BDD',
      icon: 'assets/levodopa.png',
      active: false
    },
    { label: 'Otras',
      color: '#C83387',
      icon: 'assets/otros.png',
      active: false
    },
    { label: 'Eventos',
      color: '#06387A',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Rutinas',
      color: '#E88AA1',
      icon: 'assets/rutina.png',
      active: false
    } ];

  eventsType: any[] = [
    { label: 'Congelamiento',
      color: '#55D6BE',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Lentificacion',
      color: '#669BBC',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Discinecias',
      color: '#5B4E77',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Temblor',
      color: '#E4572E',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Caidas',
      color: '#A8C686',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Tropezones',
      color: '#B68F40',
      icon: 'assets/eventos.png',
      active: false
    },
    { label: 'Otro',
      color: '#0D448B',
      icon: 'assets/eventos.png',
      active: false
    } ];

  patient: any = {
    name: 'Jaime Andres Aristizabal',
    document: '11440593994',
    age: '24',
    gender: 'Masculino'
  }

  doctor: any = {
    name: 'Humberto Loaiza Correa'
  }
  // Estas son las variables internas
  allActiveEvent: boolean;
  lastEventSelect: string;
  @Output() filter: EventEmitter<string>;
  eventActive: boolean;

  constructor() {
    this.lastEventSelect = '';
    this.allActiveEvent = false;
    this.eventActive = false;
    this.filter = new EventEmitter();
  }

  mouseOverEvent(event: any) {
    document.getElementById(event.label).style.borderColor = '#FFFFFF';
  }

  mouseLeaveEvent(event: any) {
    if (!event.active) {
      document.getElementById(event.label).style.borderColor = '#000000';
    }
  }

  actionClickEvents(event: any) {
    if (event.label === this.events[3].label) {
      this.eventActive = true;
    } else {
      this.eventActive = false;
    }

    if (event.label === this.lastEventSelect) {
      let color;
      let status;
      if (this.allActiveEvent) {
        color = '#000000';
        status = false;
        this.allActiveEvent = false;
        this.lastEventSelect = '';
      } else {
        color = '#FFFFFF';
        status = true;
        this.allActiveEvent = true;
        this.lastEventSelect = event.label;
      }
      for (const item of this.events) {
        document.getElementById(item.label).style.borderColor = color;
        item.active = status;
      }
    } else {
      if (event.active) {
        this.filter.emit(event.label);
        document.getElementById(event.label).style.borderColor = '#000000';
        event.active = false;
      } else {
        this.filter.emit(event.label);
        document.getElementById(event.label).style.borderColor = '#FFFFFF';
        event.active = true;
      }
      this.lastEventSelect = event.label;
    }
  }

}

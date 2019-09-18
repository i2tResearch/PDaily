import { Component, OnInit } from '@angular/core';
import { BehaviorElement } from './diagnostic-table.interface';

@Component({
  selector: 'app-diagnostic-table',
  templateUrl: './diagnostic-table.component.html',
  styleUrls: ['./diagnostic-table.component.css']
})

export class DiagnosticTableComponent implements OnInit {

  patientBehavior: BehaviorElement[][];
  days: number[] = [1, 2, 3, 4, 5, 6, 7];
  hours: string[] = ['6am', '7am', '8am', '9am', '10am', '11am', '12am',
                     '1pm', '2pm', '3pm', '4pm', '5pm', '6pm', '7pm', '8pm', '9pm', '10pm',
                     '11pm', '12am', '1am', '2am', '3am', '4am', '5am'];

  activeComida: boolean;
  constructor() {
    this.activeComida = false;
    this.patientBehavior = [
      [ {id: '0', color: '#2FA33C'}, {id: '1', color: '#2FA33C'},
        {id: '2', color: '#2FA33C'}, {id: '3', color: '#2FA33C'},
        {id: '4', color: '#2FA33C'}, {id: '5', color: '#2FA33C'},
        {id: '6', color: '#2FA33C'}, {id: '7', color: '#2FA33C'},
        {id: '8', color: '#2FA33C'}, {id: '9', color: '#2FA33C'},
        {id: '10', color: '#2FA33C'}, {id: '11', color: '#2FA33C'},
        {id: '12', color: '#2FA33C'}, {id: '13', color: '#2FA33C'},
        {id: '14', color: '#2FA33C'}, {id: '15', color: '#2FA33C'},
        {id: '16', color: '#2FA33C'}, {id: '17', color: '#2FA33C'},
        {id: '18', color: '#2FA33C'}, {id: '19', color: '#2FA33C'},
        {id: '20', color: '#2FA33C'}, {id: '21', color: '#2FA33C'},
        {id: '22', color: '#2FA33C'}, {id: '23', color: '#2FA33C'}],

      [ {id: '24', color: '#2FA33C'}, {id: '25', color: '#2FA33C'},
        {id: '26', color: '#2FA33C'}, {id: '27', color: '#2FA33C'},
        {id: '28', color: '#2FA33C'}, {id: '29', color: '#2FA33C'},
        {id: '30', color: '#F8E83B'}, {id: '31', color: '#F8E83B'},
        {id: '32', color: '#F8E83B'}, {id: '33', color: '#F8E83B'},
        {id: '34', color: '#F8E83B'}, {id: '35', color: '#F8E83B'},
        {id: '36', color: '#F8E83B'}, {id: '37', color: '#F8E83B'},
        {id: '38', color: '#F8E83B'}, {id: '39', color: '#F8E83B'},
        {id: '40', color: '#F8E83B'}, {id: '41', color: '#F8E83B'},
        {id: '42', color: '#F8E83B'}, {id: '43', color: '#F8E83B'},
        {id: '44', color: '#F8E83B'}, {id: '45', color: '#F8E83B'},
        {id: '46', color: '#F8E83B'}, {id: '47', color: '#F8E83B'}],

      [ {id: '48', color: '#EC4E1E'}, {id: '49', color: '#EC4E1E'},
        {id: '50', color: '#EC4E1E'}, {id: '51', color: '#F8E83B'},
        {id: '52', color: '#F8E83B'}, {id: '53', color: '#F8E83B'},
        {id: '54', color: '#F8E83B'}, {id: '55', color: '#F8E83B'},
        {id: '56', color: '#F8E83B'}, {id: '57', color: '#F8E83B'},
        {id: '58', color: '#F8E83B'}, {id: '59', color: '#F8E83B'},
        {id: '60', color: '#2FA33C'}, {id: '61', color: '#2FA33C'},
        {id: '62', color: '#2FA33C'}, {id: '63', color: '#2FA33C'},
        {id: '64', color: '#2FA33C'}, {id: '65', color: '#2FA33C'},
        {id: '66', color: '#2FA33C'}, {id: '67', color: '#2FA33C'},
        {id: '68', color: '#2FA33C'}, {id: '69', color: '#2FA33C'},
        {id: '70', color: '#2FA33C'}, {id: '71', color: '#2FA33C'}],

      [ {id: '72', color: '#F9B031'}, {id: '73', color: '#F9B031'},
        {id: '74', color: '#F9B031'}, {id: '75', color: '#F9B031'},
        {id: '76', color: '#F9B031'}, {id: '77', color: '#F9B031'},
        {id: '78', color: '#F9B031'}, {id: '79', color: '#F9B031'},
        {id: '80', color: '#F9B031'}, {id: '81', color: '#F9B031'},
        {id: '82', color: '#F9B031'}, {id: '83', color: '#F9B031'},
        {id: '84', color: '#F9B031'}, {id: '85', color: '#F9B031'},
        {id: '86', color: '#F9B031'}, {id: '87', color: '#F9B031'},
        {id: '88', color: '#F9B031'}, {id: '89', color: '#F9B031'},
        {id: '90', color: '#F9B031'}, {id: '91', color: '#F9B031'},
        {id: '92', color: '#F9B031'}, {id: '93', color: '#F9B031'},
        {id: '94', color: '#F9B031'}, {id: '95', color: '#F9B031'}],

      [ {id: '101', color: '#2FA33C'}, {id: '102', color: '#2FA33C'},
        {id: '103', color: '#2FA33C'}, {id: '104', color: '#2FA33C'},
        {id: '105', color: '#2FA33C'}, {id: '106', color: '#2FA33C'},
        {id: '107', color: '#2FA33C'}, {id: '108', color: '#2FA33C'},
        {id: '109', color: '#2FA33C'}, {id: '110', color: '#2FA33C'},
        {id: '111', color: '#2FA33C'}, {id: '112', color: '#2FA33C'},
        {id: '113', color: '#2FA33C'}, {id: '114', color: '#2FA33C'},
        {id: '115', color: '#2FA33C'}, {id: '116', color: '#2FA33C'},
        {id: '117', color: '#2FA33C'}, {id: '118', color: '#2FA33C'},
        {id: '119', color: '#2FA33C'}, {id: '120', color: '#2FA33C'},
        {id: '121', color: '#2FA33C'}, {id: '122', color: '#2FA33C'},
        {id: '123', color: '#2FA33C'}, {id: '124', color: '#2FA33C'}],

      [ {id: '201', color: '#F8E83B'}, {id: '202', color: '#F8E83B'},
        {id: '203', color: '#F8E83B'}, {id: '204', color: '#F8E83B'},
        {id: '205', color: '#F8E83B'}, {id: '206', color: '#F8E83B'},
        {id: '207', color: '#F8E83B'}, {id: '208', color: '#F8E83B'},
        {id: '209', color: '#F8E83B'}, {id: '210', color: '#F8E83B'},
        {id: '211', color: '#F8E83B'}, {id: '212', color: '#F8E83B'},
        {id: '213', color: '#F8E83B'}, {id: '214', color: '#F8E83B'},
        {id: '215', color: '#F8E83B'}, {id: '216', color: '#F8E83B'},
        {id: '217', color: '#F8E83B'}, {id: '218', color: '#F8E83B'},
        {id: '219', color: '#F8E83B'}, {id: '220', color: '#F8E83B'},
        {id: '221', color: '#F8E83B'}, {id: '222', color: '#EC4E1E'},
        {id: '223', color: '#EC4E1E'}, {id: '224', color: '#EC4E1E'},
      ],

      [ {id: '301', color: '#2FA33C'}, {id: '302', color: '#2FA33C'},
        {id: '303', color: '#2FA33C'}, {id: '304', color: '#2FA33C'},
        {id: '305', color: '#2FA33C'}, {id: '306', color: '#2FA33C'},
        {id: '307', color: '#2FA33C'}, {id: '308', color: '#2FA33C'},
        {id: '309', color: '#2FA33C'}, {id: '310', color: '#2FA33C'},
        {id: '311', color: '#2FA33C'}, {id: '312', color: '#2FA33C'},
        {id: '313', color: '#2FA33C'}, {id: '314', color: '#2FA33C'},
        {id: '315', color: '#2FA33C'}, {id: '316', color: '#2FA33C'},
        {id: '317', color: '#2FA33C'}, {id: '318', color: '#2FA33C'},
        {id: '319', color: '#2FA33C'}, {id: '320', color: '#2FA33C'},
        {id: '321', color: '#2FA33C'}, {id: '322', color: '#2FA33C'},
        {id: '323', color: '#2FA33C'}, {id: '324', color: '#2FA33C'}
      ]
    ];
  }

  ngOnInit() {

  }

  initilizeColor(behavior: BehaviorElement) {
    document.getElementById(behavior.id).style.backgroundColor = behavior.color;
  }

  getColor() {
    return 'background: #FFFFFF';
  }

  hoverUp(behavior: BehaviorElement){
    document.getElementById(behavior.id).style.backgroundColor = "#FFFFFF";
  }

  showGridEvent(event: string) {
    console.log(event);
    document.getElementById(this.patientBehavior[0][1].id).style.background = "#FFFFFF";
    document.getElementById(this.patientBehavior[1][14].id).style.background = "#FFFFFF";

    document.getElementById(this.patientBehavior[2][1].id).style.background = "#FFFFFF";

    document.getElementById(this.patientBehavior[3][15].id).style.background = "#FFFFFF";

    document.getElementById(this.patientBehavior[4][1].id).style.background = "#FFFFFF";

  }
}

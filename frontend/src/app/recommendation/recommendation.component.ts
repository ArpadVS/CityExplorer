import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ActivityService } from '../core/services/activity.service';
import { ToastrService } from 'ngx-toastr';
import { UserRequirementsDTO } from '../models/requirements.model';
import { Activity } from '../models/activity.model';
import { VirtualTimeScheduler } from 'rxjs';

@Component({
  selector: 'app-recommendation',
  templateUrl: './recommendation.component.html',
  styleUrls: ['./recommendation.component.scss']
})
export class RecommendationComponent implements OnInit {
  showForm: boolean;
  showNumber: boolean;
  showResult: boolean;
  userRequirements: UserRequirementsDTO;
  requirementsForm: FormGroup;

  recommendedActivity: Activity;


  constructor(
    private activityService: ActivityService,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.createForm();
    this.showNumber = true;
    this.showForm = true;
    this.showResult = false;
    this.userRequirements = new UserRequirementsDTO();
  }

  createForm(){
    this.requirementsForm = this.fb.group({
      transportation: ['', Validators.required],
      free: [false, Validators.nullValidator],
      cheap: [false, Validators.nullValidator],
      moderate: [false, [Validators.nullValidator]],
      expensive: [false, [Validators.nullValidator]],
      companion: ['', Validators.required],
      numPeople: [1, Validators.nullValidator],
      theme: ['', Validators.required],
      special: ['', Validators.required]
    });
  }

  createDTO(){
    let formVal = this.requirementsForm.value;
    //console.log(formVal);
    this.userRequirements.companion = formVal.companion;
    this.userRequirements.numPeople = formVal.numPeople;
    this.userRequirements.price = formVal.price;
    this.userRequirements.special = formVal.special == "true";
    this.userRequirements.theme = formVal.theme;
    this.userRequirements.transportation = formVal.transportation;

    let price : String[] = [];
    if(formVal.cheap){
      price.push('CHEAP');
    }
    if(formVal.moderate){
      price.push('MODERATE');
    }
    if(formVal.free){
      price.push('FREE');
    }
    if(formVal.expensive){
      price.push('EXPENSIVE');
    }
    this.userRequirements.price = price;
    console.log(this.userRequirements);

  }

  
  submit() {
    
    let v = this.requirementsForm.value;
    let priceChosen = false;
    if(v.free || v.cheap || v.moderate || v.expensive){
      priceChosen = true;
    }

    if(!this.requirementsForm.valid || !priceChosen){
      this.toastr.warning("Please answer every question.")
      return;
    }
    this.toastr.info('Waiting for recommendation');
    this.createDTO();

    this.activityService.getRecommendation(this.userRequirements as UserRequirementsDTO).subscribe(
      result => {
        this.recommendedActivity = result;
        this.showForm = false;
        this.showResult = true;
        this.toastr.success('Succesful recommendation!');

      },
      error => {
        this.requirementsForm.reset();
        this.toastr.error('Something went wrong!\nPlease try again!');
      }
    );
  }

  checkAlone(){
    if (this.requirementsForm.value.companion ==='ALONE'){
      this.requirementsForm.value.numPeople = 1;
      this.showNumber = false;
    } else {
      this.showNumber = true;
    }
  }

}

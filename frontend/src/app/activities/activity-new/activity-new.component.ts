import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivityService } from 'src/app/core/services/activity.service';
import { Activity } from 'src/app/models/activity.model';
import { Features } from 'src/app/models/features.model';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activity-new',
  templateUrl: './activity-new.component.html',
  styleUrls: ['./activity-new.component.scss']
})
export class ActivityNewComponent implements OnInit {
  private activity: Activity;
  private newActivityForm: FormGroup;
  private priceValues: string[][] = new Array<string[]>();
  private spaceValues: string[][] = new Array<string[]>();
  private locationValues: string[][] = new Array<string[]>();
  private keywordsValues: string[][] = new Array<string[]>();

  constructor( 
    private activityService: ActivityService, 
    private toastr: ToastrService, 
    private formBuilder: FormBuilder,
    private router: Router
    ) { }

  ngOnInit() {
    this.initializeCollectionValues();
    this.buildForm();
  }

  buildForm() {
    this.newActivityForm = this.formBuilder.group({
      name: ['', [
        Validators.required
      ]],
      description: ['', [
        Validators.required
      ]],
      address: ['', [
        Validators.required
      ]],
      imageUrl: ['', [
        Validators.required
      ]],
      price: ['', [
        Validators.required
      ]],
      keywords: [[], [
        Validators.required
      ]],
      space: ['', [
        Validators.required
      ]],
      location: ['', [
        Validators.required
      ]],
      busNearby: [false, [
      ]],
      childrensProgram: [false, [
      ]],
      outdoor: [false, [
      ]],
      reservations: [false, [
      ]],
      parking: [false, [
      ]],
      wifi: [false, [
      ]],
      tv: [false, [
      ]]
    });
  }

  createNewActivity() {
    this.getData();
    console.log(this.activity);
    this.activityService.newActivity(this.activity).subscribe(
      (response => {
        if (response === true) {
          this.toastr.info('New activity created!');
          this.newActivityForm.reset();
        }
      }),
      (error => {
        this.toastr.error("An error occured!");
      })
    );
  }

  getData() {
    this.activity = new Activity();
    this.activity.name= this.newActivityForm.controls.name.value;
    this.activity.address= this.newActivityForm.controls.address.value;
    this.activity.description= this.newActivityForm.controls.description.value;
    this.activity.imageUrl= this.newActivityForm.controls.imageUrl.value;
    this.activity.location = this.newActivityForm.controls.location.value;
    this.activity.features = new Features();
    this.activity.features.parking = this.newActivityForm.controls.parking.value;
    this.activity.features.busNearby = this.newActivityForm.controls.busNearby.value;
    this.activity.features.reservations = this.newActivityForm.controls.reservations.value;
    this.activity.features.wifi = this.newActivityForm.controls.wifi.value;
    this.activity.features.tv = this.newActivityForm.controls.tv.value;
    this.activity.features.childrensProgram = this.newActivityForm.controls.childrensProgram.value;
    this.activity.features.outdoor = this.newActivityForm.controls.outdoor.value;
    this.activity.features.space = this.newActivityForm.controls.space.value;
    this.activity.features.price = this.newActivityForm.controls.price.value;
    this.activity.features.keywords = this.newActivityForm.controls.keywords.value;
  }



  initializeCollectionValues() {
    this.priceValues.push(['Free', 'FREE']);
    this.priceValues.push(['Cheap', 'CHEAP']);
    this.priceValues.push(['Moderate', 'MODERATE']);
    this.priceValues.push(['Expensive', 'EXPENSIVE']);

    this.locationValues.push(['City center', 'CITY_CENTER']);
    this.locationValues.push(['Suburbs', 'SUBURBS']);
    this.locationValues.push(['Outside the city', 'OUTSIDE_THE_CITY']);

    this.spaceValues.push(['Small', 'SMALL']);
    this.spaceValues.push(['Medium', 'MEDIUM']);
    this.spaceValues.push(['Large', 'LARGE']);
    
    this.keywordsValues.push(['Romantic', 'ROMANTIC']);
    this.keywordsValues.push(['Chill', 'CHILL']);
    this.keywordsValues.push(['Adventure', 'ADVENTURE']);
    this.keywordsValues.push(['Teambuilding', 'TEAMBUILDING']);
    this.keywordsValues.push(['Luxury', 'LUXURY']);
    this.keywordsValues.push(['Family friendly', 'FAMILY_FRIENDLY']);
    this.keywordsValues.push(['Educational', 'EDUCATIONAL']);
    this.keywordsValues.push(['Nature', 'NATURE']);
    this.keywordsValues.push(['Sport', 'SPORT']);
    this.keywordsValues.push(['History', 'HISTORY']);
    this.keywordsValues.push(['Adrenaline', 'ADRENALINE']);

  }


}

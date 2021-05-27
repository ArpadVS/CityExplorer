import { NgModule } from '@angular/core';
import {
  MatIconModule,
  MatSnackBarModule,
  MatButtonModule,
  MatCardModule,
  MatMenuModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatInputModule,
  MatTooltipModule,
  MatToolbarModule,
  MatSliderModule,
  MatGridListModule,
  MatExpansionModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatSelectModule,
  MatListModule,
  MatCheckboxModule,
  MatTabsModule,
  MatDialogModule,
  MatTableModule,
  MatSlideToggleModule,
  MatPaginatorModule,
  MatAutocompleteModule,
  MatSortModule,
  MatRadioModule,
  MatChipsModule
} from '@angular/material';
import { NumberPickerModule } from 'ng-number-picker';
import { CommonModule } from '@angular/common';

const MaterialComponents = [
  MatButtonModule,
  MatCheckboxModule,
  MatInputModule,
  MatIconModule,
  MatToolbarModule,
  MatMenuModule,
  MatTableModule,
  MatSlideToggleModule,
  MatCardModule,
  MatTooltipModule,
  MatPaginatorModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatAutocompleteModule,
  MatDialogModule,
  MatListModule,
  MatTabsModule,
  MatSnackBarModule,
  MatSidenavModule,
  MatFormFieldModule,
  MatSliderModule,
  MatGridListModule,
  MatExpansionModule,
  MatSortModule,
  MatRadioModule,
  MatChipsModule,
  NumberPickerModule
];


@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents]
})
export class MaterialModule { }

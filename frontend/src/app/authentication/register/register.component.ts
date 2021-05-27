import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { Registration } from 'src/app/models/registration.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForm: FormGroup;
  registration: Registration;
  errorMessage = '';
  selected = 'buyer';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private toastr: ToastrService) {  }

  ngOnInit() {
    this.createForm();
  }

  createForm(): void {
    this.registrationForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit() {
    this.registration = this.registrationForm.value;
    console.log(this.registration);

    console.log(this.registration);
    this.authenticationService.register(this.registration as Registration).subscribe(
      result => {
        this.errorMessage = '';
        this.router.navigate(['profile']);
        this.toastr.success('Succesful registration!');
      },
      error => {
        this.registrationForm.reset();
        this.toastr.error('Email or username taken!\nTry again!');
        this.errorMessage = 'Email or username taken!\nTry again!';
      }
    );
  }


}

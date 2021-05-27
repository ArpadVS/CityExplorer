import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { User } from '../../models/user.model';
import { AuthenticationService } from 'src/app/core/services/authentication.service';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ErrorStateMatcher} from '@angular/material/core';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  selectedFile: File = null;
  user: User;
  userForm: FormGroup;
  imageUrl = 'https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png';

  loading = false;
  submitted = false;
  oldUsername: string;

  errorMessage = '';

  matcher = new MyErrorStateMatcher();

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private toastr: ToastrService,
    private renderer: Renderer2
  ) {
    this.user = {
      id: NaN,
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      registrationTime: '',
      role: ''
  };
    this.createForm();
  }

  ngOnInit() {
    this.initUser();
  }

  initUser() {
    this.authService.whoAmI().subscribe(
      result => {
        console.log(result);
        this.errorMessage = '';
        this.user = result;
        this.createForm();
      },
      error => {
        this.errorMessage = 'Please log in again to resolve errors!';
        this.toastr.error('Please log in again to resolve errors!', 'An error accured');
      }
    );
  }

  onChange(event: any) {
    event.preventDefault();
    this.imageUrl = event.target.value;
  }

  createForm() {
    this.userForm = this.fb.group({
      id: [this.user.id, Validators.required],
      username: [this.user.username, [Validators.required, Validators.minLength(3)]],
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      email: [this.user.email, [Validators.required, Validators.email ]]
    });
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.userForm.invalid) {
      this.toastr.warning('Data is not valid');
      this.errorMessage = 'Data is not valid';
      return;
    }

    let isUsernameChanged = true;

    if (this.oldUsername === this.userForm.value.username) {
      isUsernameChanged = false;
    }

    this.loading = true;
    const updUser: User = new User();
    updUser.id = this.user.id;
    updUser.username = this.userForm.value.username;
    updUser.firstName = this.userForm.value.firstName;
    updUser.lastName = this.userForm.value.lastName;
    updUser.email = this.userForm.value.email;
    console.log(updUser);
    /*
    this.userService.updateUser(updUser).subscribe (
      success => {
        this.loading = false;
        this.toastr.success('Profile updated succesfully!', 'Succes');
        this.errorMessage = 'Profile updated succesfully!';
        if (isUsernameChanged) {
          this.toastr.success('Log in with new username!', 'Username changed');
          localStorage.removeItem('user');
          this.router.navigate(['/login']);
        }
      },
      error => {
        // dodaj warning ako same email ili ako same username
        this.toastr.error('Username or email is taken!');
        this.errorMessage = 'Username or email is taken!';
        this.userForm.value.email = this.user.email;
        this.userForm.value.username = this.user.username;
        this.loading = false;
      }
    );*/
  }

  reset(event: any) {
    event.preventDefault();
    this.createForm();
  }
}

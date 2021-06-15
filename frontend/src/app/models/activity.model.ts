import {Report} from './reports.model';
import {Features} from './features.model';

export class Activity{
    id: number;
    name: String;
    description: String;
    location: String;
    address: String;
    imageUrl: String;
    features: Features;
    report: Report;
    averageRating: number;
    alarmCreation: Date
}
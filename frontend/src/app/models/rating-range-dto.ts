export class RatingRangeDTO {
    minRating: number;
    maxRating: number;

    constructor(minRating: number, maxRating: number) {
        this.minRating = minRating;
        this.maxRating = maxRating;
    }
}

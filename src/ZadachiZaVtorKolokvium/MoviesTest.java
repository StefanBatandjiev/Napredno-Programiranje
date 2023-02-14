package src.ZadachiZaVtorKolokvium;

import java.util.*;
import java.util.stream.Collectors;

class Movie{

    private String title;
    private int [] ratings;
    static int Max=Integer.MAX_VALUE;

    public Movie(String title, int [] ratings) {
        this.title = title;
        this.ratings = ratings;
        if (this.ratings.length>=Max){
            Max = this.ratings.length;
        }
    }

    public String getTitle() {
        return title;
    }

    public int[] getRatings() {
        return ratings;
    }

    public static int getMax() {
        return Max;
    }

    public int getLength(){
        return this.ratings.length;
    }

    public double getCoef(){
        return getAvgRating()*getLength()/(getMax()*1.0);
    }

    public int getSum(){
        int sum=0;
        for(int i=0;i<getLength();i++){
            sum += this.ratings[i];
        }
        return sum;
    }

    public double getAvgRating(){
        return (double)getSum()/getLength();
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",this.title,getAvgRating(),getLength());
    }
}

class MoviesList{

    private List<Movie> movies;
    private static final Comparator<Movie> avgRatingComparator = Comparator.comparing(Movie::getAvgRating).reversed().thenComparing(Movie::getTitle);
    private static final Comparator<Movie> coefComparator = Comparator.comparing(Movie::getCoef).reversed().thenComparing(Movie::getTitle);

    public MoviesList() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(String title,int [] ratings){
        this.movies.add(new Movie(title,ratings));
    }

    public List<Movie> top10ByAvgRating(){
        return this.movies.stream().sorted(avgRatingComparator).limit(10).collect(Collectors.toList());
    }

    public List<Movie> top10ByRatingCoef(){
        return this.movies.stream().sorted(coefComparator).limit(10).collect(Collectors.toList());
    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
package brickset;

import repository.Repository;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /* -first Method
     */
    /**
     * Returns a List of the LEGO set numbers, which have less than or equal tags.
     *
     * @param MaxTags the maximum number.
     * @return a {@code List<String>} of the LEGO set numbers, which have less than or equal tags.
     */
    public List<String> getNumbersWithLessThanGivenTags(int MaxTags) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().size() <= MaxTags)
                .map(LegoSet::getNumber)
                .collect(Collectors.toList());

    }

    /* -Second Method
     */
    /**
     * Prints the sum of all LEGO's set pieces to our Display.
     */
    public void printSumOfLegoPieces() {
        getAll().stream()
                .map(LegoSet::getPieces)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }


    /* -Third Method
     */
    /**
     * Prints each LEGO set names, in which the first and the last letters are the same.
     */
    public void printLegoSetsWithSameBeginningAndEnding() {
        getAll().stream()
                .map(LegoSet::getName)
                .filter(name -> name.toLowerCase().charAt(0) == name.charAt(name.length()-1))
                .forEach(System.out::println);

    }
    /* -Fourth Method
     */
    /**
     * Returns a Map object wrapping a summary of the used packaging types and their frequency among LEGO sets.
     *
     * @return a {@code Map<PackagingType, Long>} object wrapping a summary of the used packaging types
     * and their frequency among LEGO sets.
     */
    public Map<PackagingType, Long> getPackagingTypeSummary() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getPackagingType, Collectors.counting()));
    }

    /* -Fifth Method
     */
    /**
     * Prints each LEGO set names, which start with the given string specified.
     *
     * @param beginningletter is the beginning of the needed LEGO set names.
     */
    public void printLegoSetsStartingWithGivenString(String beginningletter) {
        getAll().stream()
                .map(LegoSet::getName)
                .filter(name -> name.toLowerCase().startsWith(beginningletter.toLowerCase()))
                .forEach(System.out::println);
    }

    /* -Sixth Method
     */
    /**
     * Returns the number of LEGO sets with the tag specified.
     *
     * @param tag a LEGO set tag
     * @return the number of LEGO sets with the tag specified
     */
    public long countLegoSetsWithTag(String tag) {
        return getAll().stream()
                .filter(legoSet -> legoSet.getTags() != null && legoSet.getTags().contains(tag))
                .count();
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();


        System.out.println("\nLego numbers with less than 2 tags:");
        System.out.println(repository.getNumbersWithLessThanGivenTags(2));

        System.out.println("\nThe number of LEGO sets with the tag specified:");
        System.out.println(repository.countLegoSetsWithTag("Microscale"));

        System.out.println("\nLego names with the same character at the beginning and the ending:");
        repository.printLegoSetsWithSameBeginningAndEnding();

        System.out.println("\nA summary of the packaging types of Legos:");
        System.out.println(repository.getPackagingTypeSummary());

        System.out.println("\nLego names starting with the String which is 'Rock':");
        repository.printLegoSetsStartingWithGivenString("rock");

        System.out.println("\nThe sum of all lego pieces:");
        repository.printSumOfLegoPieces();
    }
}


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


    /**
     * Visszaadja azt a témát(Theme), amelynek legrövidebb a neve.
     *
     * @return {@code Optional<String>} objektum, amelynek legrövidebb nevet kell tartalmaznia a Themeben.
     */
    public Optional<String> getThemeWithShortestName() {
        return getAll().stream()
                .map(LegoSet::getTheme)
                .reduce((firstTheme, secondTheme) ->
                        firstTheme.length() < secondTheme.length() ? firstTheme : secondTheme);
    }


    /**
     * Egy Map Objektumot ad vissza, amely minden témát(Theme) és azok különálló altémáját(Subtheme) tartalmazza.
     *
     * @return {@code Map<String, Set<String>>} object wrapping the legosets' themes and their subthemes.
     */
    public Map<String, Set<String>> getMapOfThemesWithTheirSubthemes() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getTheme,
                        Collectors.mapping(LegoSet::getSubtheme,
                                Collectors.filtering(Objects::nonNull,
                                        Collectors.toSet()))));
    }

    /**
     * Azt adja eredményül, hogy az egyes legosettek kétszáznál több darabból állnak-e.
     *
     * @return hogy minden legosettből kétszáznál több darab van-e.
     */
    public boolean returnIfAllSetsHaveMorePiecesThanTwoHundred() {
        return getAll().stream()
                .map(LegoSet::getPieces)
                .allMatch(piece -> piece >= 200);
    }


    /**
     * Egy Map Objektumot ad vissza, amely tartalmazza a legosettek témáinak összefoglalását és azok gyakoriságát.
     *
     * @return {@code Map<String, Long>} objektum csomagolás, hány legosetnek van ugyanaz a Theme-je.
     */
    public Map<String, Long> getNumberOfSetsForEachTheme() {
        return getAll().stream()
                .collect(Collectors.groupingBy(LegoSet::getTheme, Collectors.counting()));
    }


    /**
     * Kiírja minden különálló címkét a konzolra rendezve, amelyeknek nincs Subtheme-je.
     */
    public void printAllSortedDistinctTagsThatHaveNoSubtheme() {
        getAll().stream()
                .filter(brickset -> brickset.getSubtheme() != null && brickset.getTags() != null)
                .flatMap(brickset ->  brickset.getTags().stream())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        System.out.println("\nAZ ELSO STREAM METODUSOS FELADAT");
        System.out.println("--------------------------------");


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



        System.out.println("\nA MASODIK STREAM METODUSOS FELADAT");
        System.out.println("--------------------------------");


        System.out.println("\nLegrövidebb tema nev:");
        repository.getThemeWithShortestName().ifPresent(System.out::println);

        System.out.println("\nMinden tema(Theme) a kulonallo altemakkal(Subtheme):");
        System.out.println(repository.getMapOfThemesWithTheirSubthemes());

        System.out.println("\nMinden keszletben ketszaznal tobb darab van?\n");
        System.out.println(repository.returnIfAllSetsHaveMorePiecesThanTwoHundred());

        System.out.println("\nAz egyes temakhoz(Themehez) tartozo keszletek szama:\n");
        System.out.println(repository.getNumberOfSetsForEachTheme());

        System.out.println("\nRendezett es kulonallo legoset Tag-ek, amelyeknek nincs Subtheme-juk:\n");
        repository.printAllSortedDistinctTagsThatHaveNoSubtheme();
    }
}


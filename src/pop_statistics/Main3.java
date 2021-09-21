package pop_statistics;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3
{

  static DeathStats stats = null;
  static Map<Integer, AgePyramid> pyramids = new HashMap<Integer, AgePyramid>();



  public static DeathStats loadDeathFromData() throws Exception
  {
    DeathStats stats = new DeathStats( 1991, 2021 );
    stats.loadDeathRecords( "data/deces-1991.txt" );
    stats.loadDeathRecords( "data/deces-1992.txt" );
    stats.loadDeathRecords( "data/deces-1993.txt" );
    stats.loadDeathRecords( "data/deces-1994.txt" );
    stats.loadDeathRecords( "data/deces-1995.txt" );
    stats.loadDeathRecords( "data/deces-1996.txt" );
    stats.loadDeathRecords( "data/deces-1997.txt" );
    stats.loadDeathRecords( "data/deces-1998.txt" );
    stats.loadDeathRecords( "data/deces-1999.txt" );
    stats.loadDeathRecords( "data/deces-2000.txt" );
    stats.loadDeathRecords( "data/deces-2001.txt" );
    stats.loadDeathRecords( "data/deces-2002.txt" );
    stats.loadDeathRecords( "data/deces-2003.txt" );
    stats.loadDeathRecords( "data/deces-2004.txt" );
    stats.loadDeathRecords( "data/deces-2005.txt" );
    stats.loadDeathRecords( "data/deces-2006.txt" );
    stats.loadDeathRecords( "data/deces-2007.txt" );
    stats.loadDeathRecords( "data/deces-2008.txt" );
    stats.loadDeathRecords( "data/deces-2009.txt" );
    stats.loadDeathRecords( "data/deces-2010.txt" );
    stats.loadDeathRecords( "data/deces-2011.txt" );
    stats.loadDeathRecords( "data/deces-2012.txt" );
    stats.loadDeathRecords( "data/deces-2013.txt" );
    stats.loadDeathRecords( "data/deces-2014.txt" );
    stats.loadDeathRecords( "data/deces-2015.txt" );
    stats.loadDeathRecords( "data/deces-2016.txt" );
    stats.loadDeathRecords( "data/deces-2017.txt" );
    stats.loadDeathRecords( "data/deces-2018.txt" );
    stats.loadDeathRecords( "data/deces-2019.txt" );
    stats.loadDeathRecords( "data/deces-2020.txt" );

    stats.loadDeathRecords( "data/deces-2021-t1.txt" );
    stats.loadDeathRecords( "data/deces-2021-t2.txt" );
    stats.loadDeathRecords( "data/deces-2021-m07.txt" );
    stats.loadDeathRecords( "data/deces-2021-m08.txt" );

    return stats;
  }



  public static void printDeathPerAge() throws IOException
  {
    Csv csv = new Csv( "Age", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998",
        "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
        "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020",
        "2021" );
    for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
    {
      List<Object> row = new ArrayList<Object>();
      row.add( age );
      for( int year = 1991; year <= 2021; year++ )
      {
        long death = stats.getDeath( year, 0, DeathStats.MAX_DAY_COUNT, age, age, null );
        if( age == AgePyramid.MAX_AGE )
        {
          death = stats.getDeath( year, 0, DeathStats.MAX_DAY_COUNT, age, DeathStats.MAX_AGE,
              null );
        }
        long pop = pyramids.get( year ).get( age, age, null );
        row.add( death * 1000000f / pop );
      }
      csv.addRow( row );
    }

    csv.print( "docs/results/DecesParAge.csv" );
  }


  public static void printDeathPerWeekAndAge() throws IOException
  {
    Csv csv = new Csv( "semaine \\ age", "0", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60",
        "61-70", "71-80", "81-90", "90+" );
    for( int year = 2019; year <= 2021; year++ )
    {
      for( int day = 1; day < DeathStats.MAX_DAY_COUNT - 6; day += 7 )
      {
        List<Object> row = new ArrayList<Object>();
        String date = LocalDate.ofYearDay( year, day ).format( DateTimeFormatter.ISO_LOCAL_DATE );
        row.add( date );

        int fromAge = 0;
        int toAge = 0;
        long death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        long pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 1;
        toAge = 10;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 11;
        toAge = 20;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 21;
        toAge = 30;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 31;
        toAge = 40;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 41;
        toAge = 50;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 51;
        toAge = 60;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 61;
        toAge = 70;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 71;
        toAge = 80;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 81;
        toAge = 90;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        fromAge = 91;
        toAge = DeathStats.MAX_AGE;
        death = stats.getDeath( year, day, day + 6, fromAge, toAge, null );
        pop = pyramids.get( year ).get( fromAge, toAge, null );
        row.add( death * 1000000f / pop );

        csv.addRow( row );
      }
    }
    csv.print( "docs/results/DecesParSemaineEtClasseAge.csv" );
  }


  public static void printDeathPerWeek(int normalisedYear) throws IOException
  {
    Csv csv = new Csv( "semaine", "population", "deces", "taux",
        "taux normalise " + normalisedYear );
    for( int year = 1991; year <= 2021; year++ )
    {
      for( int day = 1; day < DeathStats.MAX_DAY_COUNT - 6; day += 7 )
      {
        String date = LocalDate.ofYearDay( year, day ).format( DateTimeFormatter.ISO_LOCAL_DATE );
        long death = stats.getDeath( year, day, day + 6, 0, DeathStats.MAX_AGE, null );
        long pop = pyramids.get( year ).get( 0, AgePyramid.MAX_AGE, null );
        float rate = death * 1000000f / pop;

        // compute rate for every age
        long deathAge[] = new long[AgePyramid.MAX_AGE + 1];
        long popAge[] = new long[AgePyramid.MAX_AGE + 1];
        double rateAge[] = new double[AgePyramid.MAX_AGE + 1];
        for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
        {
          if( age == AgePyramid.MAX_AGE )
          {
            deathAge[age] = stats.getDeath( year, day, day + 6, age, DeathStats.MAX_AGE, null );
          }
          else
          {
            deathAge[age] = stats.getDeath( year, day, day + 6, age, age, null );
          }
          popAge[age] = pyramids.get( year ).get( age, age, null );
          rateAge[age] = deathAge[age] * 1000000d / popAge[age];
        }
        double normalisedRate = 0;
        for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
        {
          normalisedRate += rateAge[age] * pyramids.get( normalisedYear ).getRate( age, age, null );
        }

        csv.addRow( date, pop, death, rate, normalisedRate );
      }
    }
    csv.print( "docs/results/DecesParSemaine.csv" );
  }


  public static void printDeathPerYear(int normalisedYear) throws IOException
  {
    Csv csv = new Csv( "Annee", "population", "deces", "taux", "taux normalise " + normalisedYear );
    for( int year = 1991; year <= 2021; year++ )
    {
      long death = stats.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, 0, DeathStats.MAX_AGE, null );
      long pop = pyramids.get( year ).get( 0, AgePyramid.MAX_AGE, null );
      float rate = death * 1000000f / pop;

      // compute rate for every age
      long deathAge[] = new long[AgePyramid.MAX_AGE + 1];
      long popAge[] = new long[AgePyramid.MAX_AGE + 1];
      double rateAge[] = new double[AgePyramid.MAX_AGE + 1];
      for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
      {
        if( age == AgePyramid.MAX_AGE )
        {
          deathAge[age] = stats.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, age,
              DeathStats.MAX_AGE, null );
        }
        else
        {
          deathAge[age] = stats.getDeath( year, 1, DeathStats.MAX_DAY_COUNT, age, age, null );
        }
        popAge[age] = pyramids.get( year ).get( age, age, null );
        rateAge[age] = deathAge[age] * 1000000d / popAge[age];
      }
      double normalisedRate = 0;
      for( int age = 0; age <= AgePyramid.MAX_AGE; age++ )
      {
        normalisedRate += rateAge[age] * pyramids.get( normalisedYear ).getRate( age, age, null );
      }

      csv.addRow( year, pop, death, rate, normalisedRate );
    }
    csv.print( "docs/results/DecesParAn.csv" );
  }



  public static void main(String[] args) throws Exception
  {
    pyramids = AgePyramid.load( "docs/results/PyramideDesAgesHommes.csv",
        "docs/results/PyramideDesAgesFemmes.csv" );
    // stats = loadDeathFromData();
    // stats.saveToJson( "docs/results/death-stats.json" );
    stats = DeathStats.loadFromJson( "docs/results/death-stats.json" );

    printDeathPerWeek( 2021 );
    printDeathPerYear( 2021 );
    printDeathPerWeekAndAge();
    printDeathPerAge();


  }

}

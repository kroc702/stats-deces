/**
 * 
 */
package pop_statistics;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * on line record from stat file
 *
 */
public class DeathRecord {
	static DateTimeFormatter dateFormater = DateTimeFormatter.ISO_LOCAL_DATE;
	static Random random = new Random();

	/**
	 * true : male, false : female Sexe - Longueur : 1 - Position : 81 - Type :
	 * Numérique 1 = Masculin; 2 = féminin
	 */
	boolean sexe = true;
	/**
	 * Date de naissance - Longueur : 8 - Position : 82-89 - Type : Numérique Forme
	 * : AAAAMMJJ - AAAA=0000 si année inconnue; MM=00 si mois inconnu; JJ=00 si
	 * jour inconnu
	 */
	LocalDate birth = null;

	/**
	 * DOM/TOM/COM/Pays de naissance en clair - Longueur : 30 - Position : 125-154 -
	 * Type : Alphanumérique
	 */
	boolean isBornAbroad = false;

	/**
	 * Date de décès - Longueur : 8 - Position : 155-162 - Type : Numérique Forme :
	 * AAAAMMJJ - AAAA=0000 si année inconnue; MM=00 si mois inconnu; JJ=00 si jour
	 * inconnu
	 */
	public LocalDate death = null;

	public int getDeathAge() {
		return Period.between(birth, death).getYears();
	}

	public boolean isMale() {
		return sexe;
	}

	/**
	 * 
	 * @param str
	 * @return null if parse unsuccess
	 */
	public static DeathRecord parse(String str) {
		try {
			if (str == null || str.length() < 176) {
				return null;
			}

			// fix some error
			if (str.length() > 198 && str.indexOf('Â') >= 0) {
				str = str.replace("Â", "");
			}

			DeathRecord rec = new DeathRecord();

			// sexe
			if (str.charAt(80) == '1')
				rec.sexe = true;
			else if (str.charAt(80) == '2')
				rec.sexe = false;
			else
				return null;

			// birth
			int year = Integer.valueOf(str.substring(81, 81 + 4));
			if (year <= 1850)
				return null;
			int month = Integer.valueOf(str.substring(85, 85 + 2));
			if (month == 0)
				month = random.nextInt(11) + 1;
			int day = Integer.valueOf(str.substring(87, 87 + 2));
			if (day == 0)
				day = random.nextInt(27) + 1;
			rec.birth = LocalDate.of(year, month, day);

			if (str.substring(124, 124 + 30).trim().isEmpty()) {
				rec.isBornAbroad = false;
			} else {
				rec.isBornAbroad = true;
			}

			// death
			year = Integer.valueOf(str.substring(154, 154 + 4));
			if (year <= 1900)
				return null;
			month = Integer.valueOf(str.substring(158, 158 + 2));
			if (month == 0)
				month = random.nextInt(11) + 1;
			day = Integer.valueOf(str.substring(160, 160 + 2));
			if (day == 0)
				day = random.nextInt(27) + 1;
			rec.death = LocalDate.of(year, month, day);

			return rec;
		} catch (Throwable th) {
		}
		return null;
	}

	@Override
	public String toString() {
		return "Record [sexe=" + sexe + ", birth=" + dateFormater.format(birth) + ", isBornAbroad=" + isBornAbroad
				+ ", death=" + dateFormater.format(death) + "]";
	}

	public static String csvHeader() {
		return "sexe,\tisBornAbroad,\tbornYear,\tdeathYear";
	}

	public String toCSV() {
		StringBuffer buf = new StringBuffer();
		if (sexe) {
			buf.append('M');
		} else {
			buf.append('F');
		}
		buf.append(",\t");
		if (isBornAbroad) {
			buf.append("true");
		} else {
			buf.append("false");
		}
		buf.append(",\t");
		buf.append(dateFormater.format(birth));
		buf.append(",\t");
		buf.append(dateFormater.format(death));
		return buf.toString();
	}

}

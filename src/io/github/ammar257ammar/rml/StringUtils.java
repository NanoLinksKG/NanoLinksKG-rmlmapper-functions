/**
* NanoLinksKG RML-mapper helper functions
* 
*Copyright (C) 2022  Ammar Ammar <ammar257ammar@gmail.com> ORCID:0000-0002-8399-8990
*
*This program is free software: you can redistribute it and/or modify
*it under the terms of the GNU General Public License as published by
*the Free Software Foundation, either version 3 of the License, or
*(at your option) any later version.
*
*This program is distributed in the hope that it will be useful,
*but WITHOUT ANY WARRANTY; without even the implied warranty of
*MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*GNU Affero General Public License for more details.
*
*You should have received a copy of the GNU General Public License
*along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*/

package io.github.ammar257ammar.rml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

/**
 * A helper class with static functions to be used with RML-mapper in RML
 * mapping rules
 * This class is meant to be used in the docker image of NanoLinksKG rmltools Docker image
 * 
 * @author Ammar Ammar
 *
 */

public class StringUtils {
	
	/**
	 * A method to join two strings (without delimeter)
	 * @param str1 the first string
	 * @param str2 the second string
	 * @return the joined string
	 */
	public static String join(String str1, String str2) {
		return str1 + str2;
	}

	/**
	 * A method to hash a string and concatenate it to a prefix URL string
	 * @param prefix a string value of the prefix URL
	 * @param strToHash a string to be hashed using MD5 algorithm
	 * @return a string composed from the prefix URL and the MD5 hashed string
	 * @throws NoSuchAlgorithmException if the hashing algorithm does not exist
	 */
	public static String hashAndJoin(String prefix, String strToHash) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(strToHash.trim().getBytes());

		byte[] digest = md.digest();

		return prefix.trim() + DatatypeConverter.printHexBinary(digest).toLowerCase();
	}
	
	/**
	 * A method to check if a string contains another string
	 * @param str1 the haystack string
	 * @param str2 the needle string
	 * @return a boolean with true value if str1 contains str2 and false otherwise
	 */
	public static boolean stringContains(String str1, String str2) {
		return str1.contains(str2);
	}

	
	/**
	 * A method to check if a string does not contain another string
	 * @param str1 the haystack string
	 * @param str2 the needle string
	 * @return a boolean with false value if str1 contains str2 and true otherwise
	 */
	public static boolean stringNotContains(String str1, String str2) {
		return !str1.contains(str2);
	}

	/**
	 * A method to check if a string is empty
	 * @param str1 the input string
	 * @return a boolean with true value if the string is empty and false otherwise
	 */
	public static boolean stringNotEmpty(String str1) {
		return !"".equals(str1.trim());
	}

	/**
	 * A method to check if a string (except a few from list) does not contain another string 
	 * @param str1 str1 the haystack string 
	 * @param str2 str2 the needle string
	 * @param str3 the exception list of strings separated by #
	 * @return a boolean value
	 */
	public static boolean stringNotContainsExcept(String str1, String str2, String str3) {

		str1 = str1.trim();
		str2 = str2.trim();
		str3 = str3.trim();

		List<String> list = Arrays.asList(str3.split("#"));

		if (!str1.contains(str2)) {
			if (list.contains(str1)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * A method to check if a string represent a range (e.g. N-M, &lt;N, &gt;N)
	 * @param str1 the string to check for range
	 * @return a boolean with true value if the string represent a range and false otherwise
	 */
	public static boolean isRangeValue(String str1) {

		str1 = str1.trim();
		
		if ((str1.contains("-") && str1.indexOf('-') != 0) || str1.startsWith("<") || str1.startsWith(">")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A method to check if two strings represent a value with a qualifier
	 * @param str1 the string of the value
	 * @param str2 the string of the qualifer
	 * @return a boolean value
	 */
	public static boolean isValueWithQualifier(String str1, String str2) {

		str1 = str1.trim();
		str2 = str2.trim();
		
		if ((!str1.contains("-") || str1.indexOf('-') == 0) && !str1.startsWith("<") && !str1.startsWith(">")) {

			if (str2 != null && !"".equals(str2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * A method to create a representation of a value with qualifier
	 * @param str1 the value string
	 * @param str2 the qualifer string
	 * @return a string of value with qualifier
	 */
	public static String constructValueWithQualifier(String str1, String str2) {

		str1 = str1.trim();
		str2 = str2.trim();
		
		if (str2 != null && !"".equals(str2)) {

			if (str1 != null && !"".equals(str1)) {
				return str1 + " +/- " + str2;
			} else {
				return "";
			}
		}

		return "";
	}

	/**
	 * A method to check if two strings does not represent a value with a qualifier
	 * @param str1 the string of the value
	 * @param str2 the string of the qualifer
	 * @return a boolean value
	 */
	public static boolean isValueWithoutQualifier(String str1, String str2) {

		str1 = str1.trim();
		str2 = str2.trim();

		if ((!str1.contains("-") || str1.indexOf('-') == 0) && !str1.startsWith("<") && !str1.startsWith(">")) {

			if (str2 != null && !"".equals(str2)) {
				return false;
			} else {

				if (!"".equals(str1)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * A method to fix a floating point string
	 * @param num the string of the number with floating point
	 * @return a fixed string
	 */
	public static String fixExpressionFloatingPoint(String num) {

		num = num.trim();
		num = num.replaceAll("\"", "");
		num = num.replaceAll("'", "");
		num = num.replaceAll(" ", "");

		if (num.contains(",")) {
			num = num.replaceAll(",", ".");
		}

		BigDecimal d = new BigDecimal(num);

		return d.toPlainString();
	}

	/**
	 * A method to create a mapping of key strings and their corresponding IRI annotation
	 * @return a hash map
	 * @throws FileNotFoundException in case the IRI mapping files do not exist
	 * @throws IOException in case of IO errors
	 */
	public static Map<String, Map<String, String>> getMappings() throws FileNotFoundException, IOException {

		Map<String, String> namespaces = new HashMap<String, String>();

		try (BufferedReader br = new BufferedReader(new FileReader("/properties/namespaces.tsv"))) {

			String line;

			while ((line = br.readLine()) != null) {

				String[] row = line.split("\t");

				if (!namespaces.containsKey(row[0]))
					namespaces.put(row[0].toLowerCase().trim(), row[1].trim());
			}

		}

		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		try (BufferedReader br = new BufferedReader(new FileReader("/properties/mapToIRI.tsv"))) {

			String line;

			while ((line = br.readLine()) != null) {

				String[] row = line.split("\t");

				if (row[2].contains(":")) {
					String prefix = row[2].substring(0, row[2].indexOf(":") + 1).toLowerCase().trim();
					row[2] = row[2].replace(prefix, namespaces.get(prefix));
				}

				if (map.containsKey(row[0])) {

					Map<String, String> innerMap = map.get(row[0]);

					if (!innerMap.containsKey(row[1]))
						innerMap.put(row[1].trim(), row[2].trim());

					map.put(row[0], innerMap);

				} else {

					Map<String, String> innerMap = new HashMap<String, String>();

					innerMap.put(row[1].trim(), row[2].trim());

					map.put(row[0], innerMap);

				}
			}
		}

		return map;
	}

	/** 
	 * A method to map a string input to the corresponding IRI
	 * @param str1 the group of the input string
	 * @param str2 the string to get the IRI mapping for
	 * @return a string value of the IRI mapping
	 * @throws FileNotFoundException in case the IRI mapping files do not exist
	 * @throws IOException in case of IO errors
	 */
	public static String mapToIri(String str1, String str2) throws FileNotFoundException, IOException {

		str1 = str1.trim();
		str2 = str2.trim();
		
		Map<String, Map<String, String>> map = getMappings();

		return map.get(str1).get(str2) == null ? "" : map.get(str1).get(str2);
	}

	/**
	 * A method to map a string to an IRI, hash it with another string and append the result to a prefix IRI
	 * @param prefix a string of the prefix IRI
	 * @param content a string of the first part to be hashed
	 * @param mappingGroup a string name of the group to obtain the IRI mapping
	 * @param strToMap a string to be mapped to an IRI and then hashed with 'content'
	 * @return a string of the prefix IRI with the hashed string
	 * @throws NoSuchAlgorithmException if the hashing algorithm does not exist
	 * @throws FileNotFoundException in case the IRI mapping files do not exist
	 * @throws IOException in case of IO errors
	 */
	public static String mapThenHashAndJoin(String prefix, String content, String mappingGroup, String strToMap)
			throws NoSuchAlgorithmException, FileNotFoundException, IOException {

		prefix = prefix.trim();
		content = content.trim();
		mappingGroup = mappingGroup.trim();
		strToMap = strToMap.trim();
		
		Map<String, Map<String, String>> map = getMappings();

		MessageDigest md = MessageDigest.getInstance("MD5");

		String strToHash = content
				+ (map.get(mappingGroup).get(strToMap) == null ? strToMap : map.get(mappingGroup).get(strToMap));

		md.update(strToHash.getBytes());

		byte[] digest = md.digest();

		return prefix.trim() + DatatypeConverter.printHexBinary(digest).toLowerCase();
	}

}

package com.tournament.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rating {

	public static void main(String[] args) {
		System.out.println("Punteggio aggiornato: " + aggiornaRating(new BigDecimal(1750), new BigDecimal(1750), "vittoria"));
	}

	public static BigDecimal aggiornaRating(BigDecimal ratingInizialePlayer1, BigDecimal ratingInizialePlayer2,
			String result) {
		if (result == "vittoria") {

			if (ratingInizialePlayer1.compareTo(ratingInizialePlayer2) == 1) {
				return ratingInizialePlayer1.add(calcolaVariazioneRating(ratingInizialePlayer2, ratingInizialePlayer1));
			}
			// in caso di pareggio o player1<player2
			return ratingInizialePlayer1.add(calcolaVariazioneRating(ratingInizialePlayer2, ratingInizialePlayer1));
		} else {
			if (ratingInizialePlayer1.compareTo(ratingInizialePlayer2) == 1) {
				return ratingInizialePlayer1
						.subtract(calcolaVariazioneRating(ratingInizialePlayer2, ratingInizialePlayer1));
			}else {
				return ratingInizialePlayer1.subtract(calcolaVariazioneRating(ratingInizialePlayer1, ratingInizialePlayer2));
			}

		}
	}

	public static BigDecimal calcolaVariazioneRating(BigDecimal ratingInizialePlayer1,
			BigDecimal ratingInizialePlayer2) {
		int coefficienteRating = 20;
		// BigDecimal punteggioAtteso = 1/(1+10^(RD/400);
		BigDecimal denominatorePotenza = new BigDecimal(400);
		BigDecimal ratingDifference = ratingInizialePlayer1.subtract(ratingInizialePlayer2);
		// RD/400
		denominatorePotenza = ratingDifference.divide(denominatorePotenza);
		BigDecimal baseDieci = new BigDecimal(10);
		// 1+10^(rd/400)
		BigDecimal denominatore = new BigDecimal(1).add(calcolaPotenzaBigDecimal(baseDieci, denominatorePotenza));
		denominatore = denominatore.setScale(3, RoundingMode.HALF_EVEN);
		BigDecimal risultatoAtteso = BigDecimal.ONE.divide(denominatore, 3, RoundingMode.HALF_EVEN);
		BigDecimal variazioneRating = new BigDecimal(coefficienteRating)
				.multiply(BigDecimal.ONE.subtract(risultatoAtteso));
		System.out.println("Variazione del rating: "+variazioneRating);
		return variazioneRating;
	}

	public static BigDecimal calcolaPotenzaBigDecimal(BigDecimal base, BigDecimal exponent) {
		BigDecimal result = BigDecimal.ZERO;
		int signOf2 = exponent.signum();
		double dn1 = base.doubleValue();
		BigDecimal n2 = exponent.multiply(new BigDecimal(signOf2)); // n2 is now positive
		BigDecimal remainderOf2 = n2.remainder(BigDecimal.ONE);
		BigDecimal n2IntPart = n2.subtract(remainderOf2);
		BigDecimal intPow = base.pow(n2IntPart.intValueExact());
		BigDecimal doublePow = new BigDecimal(Math.pow(dn1, remainderOf2.doubleValue()));
		result = intPow.multiply(doublePow);

		// Fix negative power
		if (signOf2 == -1)
			result = BigDecimal.ONE.divide(result, 3, RoundingMode.HALF_EVEN);
		return result;
	}

}

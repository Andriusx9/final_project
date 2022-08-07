package com.example.finalProject.service;

public class NumbersToWords {

    private static final String[] units = {"", "vienas", "du", "trys", "keturi", "penki",
            "šeši", "septyni", "aštuoni", "devyni"};

    private static final String[] niolika = {"", "vienuolika", "dvylika", "trylika", "keturiolika",
            "penkiolika", "šešiolika", "septyniolika", "aštuoniolika", "devyniolika"};

    private static final String[] tens = {"", "dešimt", "dvidešimt", "trisdešimt", "keturiasdešimt",
            "penkiasdešimt", "šešiasdešimt", "septyniasdešimt", "aštuoniasdešimt", "devyniasdešimt"};

    private static final String[][] name = {
            {"milijonas", "milijonai", "milijonų"},
            {"tūkstantis", "tūkstančiai", "tūkstančių"}
    };

    public static String numbersToWords(Double number){

        //System.out.println(String.format("%.2f", number));

        String[] split = String.valueOf(number).split("\\.");

        String numberToWordsBeforePoint = numbersToWords(Integer.parseInt(split[0]));

        String cents = String.valueOf(split[1]);

        System.out.println("lenght: "+ cents.length());
        if( cents.length() == 1){
            cents = cents.concat("0");
        }
        return numberToWordsBeforePoint.concat(" ").concat(cents);
    }

    public static String numbersToWordsWithEuros(double number) {
        String[] split = String.valueOf(number).split("\\.");
        String numberToWordsBeforePoint = numbersToWordsWithEuros(Integer.parseInt(split[0]));
        String cents = String.valueOf(split[1]);

        int euros= Integer.parseInt(split[0]);
        String numberInWordsBeforePoint = numbersToWordsWithEuros(euros);

        String finalCents;

        if(String.valueOf(cents).length() > 1) {
            finalCents = String.valueOf(cents).substring(0, 2);
        } else {
            finalCents = String.valueOf(cents).substring(0, 1);
        }



        String[] splitForChangeCents = numberInWordsBeforePoint.split("0");

        return splitForChangeCents[0].concat(finalCents).concat(splitForChangeCents[1]);


    }

    public static String numbersToWordsWithEuros(int number){
        String numberInWords = numbersToWords(number);
        String eur = " ";
        int numberLength = String.valueOf(number).length();

        if(numberLength == 1) {
            if(number == 1) {
                eur = eur.concat("euras");
            } else if( number > 1 & number < 10){
                eur = eur.concat("eurai");
            }
        } else if(numberLength == 2){
            int secondNumber = Character.getNumericValue(String.valueOf(number).charAt(1));
            if(secondNumber == 0){
                eur = eur.concat("eurų");
            } else if(number > 10 & number < 20) {
                eur = eur.concat("eurų");
            } else if( secondNumber == 1) {
                eur = eur.concat("euras");
            } else if( secondNumber > 1 & secondNumber < 10) {
                eur = eur.concat("eurai");
            }
        } else if(numberLength > 2) {
            String lastTwoDigits = String.valueOf(number).substring(numberLength - 2);
            String lastDigit = String.valueOf(number).substring(numberLength - 1);

            if(Integer.parseInt(lastDigit) == 0) {
                eur = eur.concat("eurų");
            } else if(Integer.parseInt(lastTwoDigits) == 1 ) {
                eur = eur.concat("euras");
            } else if(Integer.parseInt(lastTwoDigits) > 1 & Integer.parseInt(lastTwoDigits) < 10){
                eur = eur.concat("eurai");
            } else if( Integer.parseInt(lastTwoDigits) > 10 & Integer.parseInt(lastTwoDigits) < 20) {
                eur = eur.concat("eurų");
            } else if(Integer.parseInt(lastDigit) == 1) {
                eur = eur.concat("euras");
            } else if(Integer.parseInt(lastDigit) > 1 & Integer.parseInt(lastDigit) < 10 ) {
                eur = eur.concat("eurai");
            }
        }

        return numberInWords.concat(eur + " 0 ct");

    }


    /**
     *  The number that will be translated into words
     * @param number  MIN = 1, MAX = 999 999 999
     * @return method return number translated into words
     */
    public static String numbersToWords(int number) {
        if (number == 0 || number < 0) {
            System.exit(0);
        }

        int numberLength = String.valueOf(number).length();
        String formattedNumber = String.format("%09d", number);
        String[] splitNumber = formattedNumber.split("(?<=\\G...)");

        boolean isMillionWasChecked = false;
        boolean isThousandWasChecked = false;

        String finalNumberInWords = "";

        for (String trio : splitNumber) {

            if (numberLength > 6 & !isMillionWasChecked) {
                finalNumberInWords = finalNumberInWords.concat(convertTrioName(Integer.parseInt(trio), true, false));
                isMillionWasChecked = true;

            } else if ((numberLength > 3 & !isThousandWasChecked & isMillionWasChecked || (!trio.equals("000") & numberLength < 6) & !isThousandWasChecked)) {
                finalNumberInWords = finalNumberInWords.concat(convertTrioName(Integer.parseInt(trio), false, true));
                isThousandWasChecked = true;

            } else {
                finalNumberInWords = finalNumberInWords.concat(convertTrioName(Integer.parseInt(trio), false, false));
                if (!isMillionWasChecked) {
                    isMillionWasChecked = true;
                } else if (!isThousandWasChecked) {
                    isThousandWasChecked = true;
                }
            }

        }

        return finalNumberInWords.trim();
    }

    public static String convertTrioName(int number, boolean isMillion, boolean isThousand) {
        String formattedNumber = String.format("%03d", number);
        int firstTrioDigit = Character.getNumericValue(String.valueOf(formattedNumber).charAt(0));
        int secondTrioDigit = Character.getNumericValue(String.valueOf(formattedNumber).charAt(1));
        int thirdTrioDigit = Character.getNumericValue(String.valueOf(formattedNumber).charAt(2));
        String stringNumber = String.valueOf(formattedNumber);
        int numberLastTwoDigits = Integer.parseInt(stringNumber.substring(1));
        String result = "";
        if (firstTrioDigit > 0) {
            if (firstTrioDigit > 1) {
                result = result.concat(units[firstTrioDigit]).concat(" šimtai ");

                if (numberLastTwoDigits == 0 & isMillion) {
                    result = result.concat(name[0][2]).concat(" ");
                } else if (numberLastTwoDigits == 0 & isThousand) {
                    result = result.concat(name[1][2]).concat(" ");
                }

            } else {
                result = result.concat(units[firstTrioDigit]).concat(" šimtas ");

                if (numberLastTwoDigits == 0 & isMillion) {
                    result = result.concat(name[0][2]).concat(" ");
                } else if (numberLastTwoDigits == 0 & isThousand) {
                    result = result.concat(name[1][2]).concat(" ");
                }

            }
        }

        if (numberLastTwoDigits > 10 & numberLastTwoDigits < 20) {
            result = result.concat(niolika[thirdTrioDigit]).concat(" ");

            if (isMillion) {
                result = result.concat(name[0][2]).concat(" ");
            }
            if (isThousand) {
                result = result.concat(name[1][2]).concat(" ");
            }
        } else {

            if (numberLastTwoDigits == 10) {
                result = result.concat(tens[secondTrioDigit]).concat(" ");

                if (isMillion) {
                    result = result.concat(name[0][2]).concat(" ");
                } else if (isThousand) {
                    result = result.concat(name[1][2]).concat(" ");
                }

            }

            if (numberLastTwoDigits >= 20) {
                result = result.concat(tens[secondTrioDigit]).concat(" ");

                if (thirdTrioDigit == 0 & isMillion) {
                    result = result.concat(name[0][2]).concat(" ");
                }
                if (thirdTrioDigit == 0 & isThousand) {
                    result = result.concat(name[1][2]).concat(" ");
                }
            }

            if (thirdTrioDigit >= 1) {
                result = result.concat(units[thirdTrioDigit]).concat(" ");

                if (thirdTrioDigit == 1 & isMillion) {

                    result = result.concat(name[0][0]).concat(" ");

                } else if (thirdTrioDigit > 1 & isMillion) {

                    result = result.concat(name[0][1]).concat(" ");

                }

                if (thirdTrioDigit == 1 & isThousand) {

                    result = result.concat(name[1][0]).concat(" ");

                } else if (thirdTrioDigit > 1 & isThousand) {

                    result = result.concat(name[1][1]).concat(" ");

                }

            }

        }

        return result;
    }

}
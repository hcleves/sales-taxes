# Sales Tax Problem

![](https://github.com/hcleves/sales-taxes/actions/workflows/test_maven.yml/badge.svg)

## Introduction
 Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When I purchase items I receive a receipt which lists the name of all the items and their price (including tax), finishing with the total cost of the items, and the total amounts of sales taxes paid. The rounding rules for sales tax are that for a tax rate of n%, a shelf price of p contains (np/100 rounded up to the nearest 0.05) amount of sales tax.

Write an application that prints out the receipt details ...

## Installation

- Make sure you have [Maven](https://maven.apache.org/) installed. 
- Clone this repo
- Navigate to the root folder
- Run the following command 
 ```
 mvn compile assembly:single
 ```
- Wait until it finishes (It might take a while on first run), a .jar file will be created inside a folder named "target".

## Usage

```
java -jar <jar_path> -i <input_filename> [-o output_filename --help]
```

## Examples
Input 1:
```
1 book at 12.49
1 music CD at 14.99
1 chocolate bar at 0.85
```
Output 1:
```
1 book : 12.49
1 music CD: 16.49
1 chocolate bar: 0.85
Sales Taxes: 1.50
Total: 29.83
```

Input 2:
```
1 imported box of chocolates at 10.00
1 imported bottle of perfume at 47.50
```

Output 2:
```
1 imported box of chocolates: 10.50
1 imported bottle of perfume: 54.65
Sales Taxes: 7.65
Total: 65.15
```

Input 3:
```
1 imported bottle of perfume at 27.99
1 bottle of perfume at 18.99
1 packet of headache pills at 9.75
1 box of imported chocolates at 11.25
```
Output 3:
```
1 imported bottle of perfume: 32.19
1 bottle of perfume: 20.89
1 packet of headache pills: 9.75
1 imported box of chocolates: 11.85
Sales Taxes: 6.70
Total: 74.68
```

## Architecture

This program consists in three classes:

### Product
This class is responsible for receiving a string representing the product and extracting data from it. Then, it is responsible for validating the data, classifing the product in the types given (imported, medical, food, books) and calculating the tax for the product

## Receipt
This class is responsible for receiving an input string and parsing it to feed to the Product class. It also is responsible for returning a string formatted like the output.

## App
This class is the entry point of the program. It is responsible from receiving the command line arguments, verifying them and feeding the Receipt class with data from the file input. It also redirects the output of the Receipt class either to stdout or to a file indicated by the user.
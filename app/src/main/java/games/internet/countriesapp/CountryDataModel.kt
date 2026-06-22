package games.internet.countriesapp


data class Country(
    val name: CountryName,
    val currencies: Map<String, Currency>?,
    val capital: List<String>?
)

data class CountryName(
    val common: String,
    val official: String,
    val nativeName: Map<String, NativeName>?
)

data class NativeName(
    val official: String,
    val common: String
)

data class Currency(
    val name: String,
    val symbol: String?
)
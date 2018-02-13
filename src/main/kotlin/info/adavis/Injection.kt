package info.adavis

import com.github.pgutkowski.kgraphql.KGraphQL
import com.google.gson.Gson
import java.time.LocalDate

data class Player(val name: String = "", val age: Int = 0)

object Injection {

    val gson by lazy { Gson() }

    val schema by lazy {
        KGraphQL.schema {

            configure {
                useDefaultPrettyPrinter = true
            }

            stringScalar<LocalDate> {
                serialize = { date -> date.toString() }
                deserialize = { dateString -> LocalDate.parse(dateString) }
            }

            query("player") {
                description = "This returns a NBA Player"
                resolver { -> Player("Greg", 34) }
            }

            mutation("updatePlayer") {
                description = "This updates a player"
                resolver { player: Player -> player }
            }

            type<Player> {
                description = "A NBA Player on a Team"
            }
        }
    }
}
import game.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SlideTest {

    // 6. Een steen verschuiven
    // a. Sommige stenen verplaatsen zich door te verschuiven. Een verschuiving is een verschuiving naar een
    // aangrenzend veld; dit mag een bezet of onbezet veld zijn.
    // b. Een verschuiving moet schuivend uitgevoerd kunnen worden. Dit betekent dat tijdens de verschuiving moet
    // gelden dat de laagste van de twee stapels die aan het begin- en eindpunt grenzen niet hoger mag
    // zijn dan de hoogste van de twee stapels op het begin- en eindpunt, waarbij de te verplaatsen steen niet mee telt.
    // In onderstaande figuur is een verschuiving te zien die niet schuivend uitgevoerd kan worden. Dit kan als volgt
    // formeel geïnterpreteerd worden. Noem de (aangrenzende) velden waar je van en naar wil schuiven
    // respectievelijk a en b. Zowel a als b hebben zes buren, maar maar twee daarvan zijn een buur van a én b. Noem
    // deze twee velden n1 en n2. Definieer een functie h(x) die het aantal gestapelde stenen op het veld x
    // teruggeeft: 0 als het veld leeg is, 1 als er maar één steen op het veld ligt, etc. Om een steen te
    // mogen verschuiven van a naar b moet gelden a en b aangrenzend zijn en dat min(h(n1), h(n2)) ≤ max(h(a) - 1, h(b))
    @Test
    void testIfTheMovementOfSomeTilesIsPossible() {

    }

    // c. Tijdens een verschuiving moet de steen continu in contact blijven met minstens één andere steen.
    // In onderstaande figuur is een verschuiving te zien waarbij de steen zowel voor als na de verschuiving in contact
    // met andere stenen is, maar tijdens de verschuiving niet. Deze verschuiving is dus niet toegestaan.
    @Test
    void testThatASlideIsNotPossibleWhenDuringTheSlideNoTilesAreTouched() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.SOLDIER_ANT)));
            put(new Coordinate(-1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SOLDIER_ANT)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.BEETLE)));
            put(new Coordinate(-1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SOLDIER_ANT)));
        }});
        HiveGame game = new HiveGame(board);

        assertThrows(Hive.IllegalMove.class, () -> game.move(-1, 1, 0, 1)); // w
    }

}

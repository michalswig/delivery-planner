package com.deliveryplanner.data;

import java.util.Map;
import java.util.Set;

public class DistrictCatalog {

    public static Map<String, Set<String>> getWarsawDistrictAndNeighbours() {
        return Map.ofEntries(
                Map.entry("Bemowo", Set.of("Bielany", "Wola", "Włochy")),
                Map.entry("Białołęka", Set.of("Bielany", "Targówek", "Praga-Północ")),
                Map.entry("Bielany", Set.of("Białołęka", "Żoliborz", "Bemowo")),
                Map.entry("Mokotów", Set.of("Śródmieście", "Ochota", "Włochy", "Ursynów", "Wilanów")),
                Map.entry("Ochota", Set.of("Śródmieście", "Wola", "Mokotów", "Włochy")),
                Map.entry("Praga-Południe", Set.of("Praga-Północ", "Targówek", "Wawer", "Śródmieście")),
                Map.entry("Praga-Północ", Set.of("Białołęka", "Targówek", "Praga-Południe", "Śródmieście")),
                Map.entry("Rembertów", Set.of("Wesoła", "Wawer", "Targówek")),
                Map.entry("Śródmieście", Set.of("Żoliborz", "Wola", "Ochota", "Mokotów", "Praga-Północ", "Praga-Południe")),
                Map.entry("Targówek", Set.of("Białołęka", "Praga-Północ", "Praga-Południe", "Rembertów")),
                Map.entry("Ursus", Set.of("Włochy")),
                Map.entry("Ursynów", Set.of("Mokotów", "Wilanów")),
                Map.entry("Wawer", Set.of("Praga-Południe", "Rembertów", "Wesoła", "Wilanów")),
                Map.entry("Wesoła", Set.of("Rembertów")),
                Map.entry("Wilanów", Set.of("Mokotów", "Ursynów")),
                Map.entry("Włochy", Set.of("Bemowo", "Ochota")),
                Map.entry("Wola", Set.of("Bemowo", "Żoliborz", "Śródmieście", "Ochota")),
                Map.entry("Żoliborz", Set.of("Bielany", "Wola", "Śródmieście"))
        );
    }


}

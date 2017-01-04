package de.quagilis;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/heroes")
@CrossOrigin(origins="http://localhost:3000*", maxAge=3600)
public class HeroController {

    @RequestMapping(value = "/", produces = "application/json")
    public List<Hero> getAll() {
        return asList(
                new Hero(11, "Mr. Nice", Power.REALLY_SMART),
                new Hero(12, "Narco", Power.WEATHER_CHANGER),
                new Hero(13, "Bombasto", Power.SUPER_HOT),
                new Hero(14, "Celeritas", Power.WEATHER_CHANGER),
                new Hero(15, "Magneta", Power.REALLY_SMART),
                new Hero(16, "RubberMan", Power.SUPER_FLEXIBLE),
                new Hero(17, "Dynama", Power.REALLY_SMART),
                new Hero(18, "Dr IQ", Power.REALLY_SMART, "Chuck Overstreet"),
                new Hero(19, "Magma", Power.WEATHER_CHANGER),
                new Hero(20, "Tornado", Power.WEATHER_CHANGER)
        );
    }
}

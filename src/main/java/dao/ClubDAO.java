package dao;

import entities.Club;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClubDAO implements DAO<Club> {
    private List<Club> clubs = new ArrayList<>();

    @Override
    public Optional<Club> get(int id) {
        final Club club =  Util.findInList(
                clubs,
                possibleClub -> possibleClub.getId() == id
        );

        return Optional.ofNullable(club);
    }

    public Optional<Club> getByCode(int code) {
        final Club club = Util.findInList(
                clubs,
                possibleClub -> possibleClub.getCode() == code
        );

        return Optional.ofNullable(club);
    }

    @Override
    public List<Club> getAll() {
        return clubs;
    }

    @Override
    public void save(Club club) { clubs.add(club); }
}

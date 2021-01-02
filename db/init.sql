CREATE TABLE players (
    id                  int PRIMARY KEY,
    first_name          varchar(80),
    second_name         varchar(80),
    position_id         int,
    club_code           int,
    form                varchar(80),
    price               int,
    gameweek_points     int,
    total_points        int,
    selected_by_percent varchar(80),
    influence           varchar(80),
    creativity          varchar(80),
    threat              varchar(80),
    ict_index           varchar(80)
);

CREATE TABLE clubs (
    code    int PRIMARY KEY,
    name    varchar(80),
    id      int
);
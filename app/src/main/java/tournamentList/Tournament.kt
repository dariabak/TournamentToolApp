package tournamentList

import bracket.TournamentDTO

class Tournament {
    constructor(tournamentDTO: TournamentDTO){
        this.name = tournamentDTO.name
        this.update = tournamentDTO.date
        this.numberOfTeams = tournamentDTO.numberOfTeams
        this.id = tournamentDTO.id
    }
    var name: String = ""
    var update: String = ""
    var numberOfTeams: Int = 0
    var id: String = ""
}
import repository.DatabaseFactory
import repository.conferences.ConferencesImplementation
import api.conference.conferenceDetail
import api.conference.conferences
import api.agenda.agendas
import api.agenda.agendasDetail
import api.attendee.attendees
import api.attendee.attendeesDetail
import api.bill.bills
import api.bill.billsDetail
import api.catering.caterings
import api.catering.cateringsDetail
import api.foodRestriction.foodRestrictions
import api.foodRestriction.foodRestrictionsDetail
import api.hotel.hotels
import api.hotel.hotelsDetail
import api.auth.login
import api.merchandising.merchandisings
import api.merchandising.merchandisingsDetail
import api.organizer.organizers
import api.organizer.organizersDetail
import api.partner.partners
import api.partner.partnersDetail
import api.place.placeDetail
import api.place.places
import api.present.presents
import api.present.presentsDetail
import api.rating.ratings
import api.rating.ratingsDetail
import api.service.services
import api.service.servicesDetail
import api.auth.signIn
import api.publicAttendee.publicAttendeeDetail
import api.publicAttendee.publicAttendees
import api.publicOrganizer.publicOrganizerDetail
import api.publicOrganizer.publicOrganizers
import api.publicPartner.publicPartnerDetail
import api.publicPartner.publicPartners
import api.publicSpeaker.publicSpeakerDetail
import api.publicSpeaker.publicSpeakers
import api.publicVoluntary.publicVoluntary
import api.publicVoluntary.publicVoluntaryDetail
import api.speaker.speakerDetail
import api.speaker.speakers
import api.topic.topics
import api.topic.topicsDetail
import api.transport.transports
import api.transport.transportsDetail
import api.video.videos
import api.video.videosDetail
import api.voluntary.voluntaries
import api.voluntary.voluntariesDetail
import repository.agenda.AgendasImplementation
import repository.attendee.AttendeesImplementation
import repository.bill.BillsImplementation
import repository.catering.CateringsImplementation
import repository.foodRestriction.FoodRestrictionsImplementation
import repository.hotel.HotelsImplementation
import repository.place.PlacesImplementation
import repository.merchandising.MerchandisingsImplementation
import repository.organizer.OrganizersImplementation
import repository.partner.PartnersImplementation
import repository.present.PresentsImplementation
import repository.rating.RatingsImplementation
import repository.service.ServicesImplementation
import repository.speakers.SpeakersImplementation
import repository.topic.TopicsImplementation
import repository.transport.TransportsImplementation
import repository.video.VideosImplementation
import repository.voluntary.VoluntariesImplementation
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.*
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import repository.publicAttendee.PublicAttendeesImplementation
import repository.publicOrganizer.PublicOrganizersImplementation
import repository.publicPartner.PublicPartnersImplementation
import repository.publicSpeaker.PublicSpeakersImplementation
import repository.publicVoluntary.PublicVoluntariesImplementation

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
fun Application.module() {

    install(DefaultHeaders)

    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText (e.localizedMessage, ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation) {
        gson()
    }

    install(Locations)

    DatabaseFactory.init()
    val dbConferences = ConferencesImplementation()
    val dbSpeakers = SpeakersImplementation()
    val dbHotels = HotelsImplementation()
    val dbPlaces = PlacesImplementation()
    val dbTransports = TransportsImplementation()
    val dbAttendees = AttendeesImplementation()
    val dbVoluntaries = VoluntariesImplementation()
    val dbAgendas = AgendasImplementation()
    val dbRatings = RatingsImplementation()
    val dbVideos = VideosImplementation()
    val dbFoodRestrictions = FoodRestrictionsImplementation()
    val dbPartners = PartnersImplementation()
    val dbMerchandisings = MerchandisingsImplementation()
    val dbPresents = PresentsImplementation()
    val dbServices = ServicesImplementation()
    val dbCaterings = CateringsImplementation()
    val dbOrganizers = OrganizersImplementation()
    val dbBills = BillsImplementation()
    val dbTopics = TopicsImplementation()
    val dbPublicAttendees = PublicAttendeesImplementation()
    val dbPublicOrganizers = PublicOrganizersImplementation()
    val dbPublicPartners = PublicPartnersImplementation()
    val dbPublicSpeakers = PublicSpeakersImplementation()
    val dbPublicVoluntaries = PublicVoluntariesImplementation()

    val jwtService = JWTService()
    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "exfest"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("email")
                print(claim.asString())
                val user = dbOrganizers.organizerByEmail(claim.asString())
                user
            }
        }
    }

    routing {
        home()

        //API
        conferences(dbConferences)//get
        conferenceDetail(dbConferences)//get
        hotels(dbHotels)
        hotelsDetail(dbHotels)
        places(dbPlaces)
        placeDetail(dbPlaces)
        speakers(dbSpeakers, dbPublicSpeakers)
        speakerDetail(dbSpeakers, dbPublicSpeakers)
        transports(dbTransports)
        transportsDetail(dbTransports)
        attendees(dbAttendees, dbPublicAttendees)
        attendeesDetail(dbAttendees, dbPublicAttendees)
        voluntaries(dbVoluntaries, dbPublicVoluntaries)
        voluntariesDetail(dbVoluntaries, dbPublicVoluntaries)
        agendas(dbAgendas)
        agendasDetail(dbAgendas)
        ratings(dbRatings)
        ratingsDetail(dbRatings)
        videos(dbVideos)
        videosDetail(dbVideos)
        foodRestrictions(dbFoodRestrictions)
        foodRestrictionsDetail(dbFoodRestrictions)
        partners(dbPartners, dbPublicPartners)
        partnersDetail(dbPartners, dbPublicPartners)
        merchandisings(dbMerchandisings)
        merchandisingsDetail(dbMerchandisings)
        presents(dbPresents)
        presentsDetail(dbPresents)
        services(dbServices)
        servicesDetail(dbServices)
        caterings(dbCaterings)
        cateringsDetail(dbCaterings)
        organizers(dbOrganizers,dbPublicOrganizers)
        organizersDetail(dbOrganizers)
        bills(dbBills)
        billsDetail(dbBills)
        topics(dbTopics)
        topicsDetail(dbTopics)

        signIn(dbOrganizers)
        login(dbOrganizers,jwtService)

        publicAttendees(dbPublicAttendees)
        publicAttendeeDetail(dbPublicAttendees)
        publicOrganizers(dbPublicOrganizers)
        publicOrganizerDetail(dbPublicOrganizers)
        publicPartners(dbPublicPartners)
        publicPartnerDetail(dbPublicPartners)
        publicSpeakers(dbPublicSpeakers)
        publicSpeakerDetail(dbPublicSpeakers)
        publicVoluntary(dbPublicVoluntaries)
        publicVoluntaryDetail(dbPublicVoluntaries)
    }
}



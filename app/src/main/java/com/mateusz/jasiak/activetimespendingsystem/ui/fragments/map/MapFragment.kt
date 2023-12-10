package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.mateusz.jasiak.activetimespendingsystem.BuildConfig
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseFragment
import com.mateusz.jasiak.activetimespendingsystem.databinding.FragmentMapBinding
import com.mateusz.jasiak.activetimespendingsystem.domain.model.domain.CoordinateDomain
import com.mateusz.jasiak.activetimespendingsystem.ui.activities.main.MainActivity
import com.mateusz.jasiak.activetimespendingsystem.utils.EMPTY_STRING
import com.mateusz.jasiak.activetimespendingsystem.utils.ID_SOCIAL_MEDIA_KEY
import com.mateusz.jasiak.activetimespendingsystem.utils.SHARED_PREFERENCES
import dagger.android.support.DaggerAppCompatActivity

class MapFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    override val viewModel: MapViewModel by lazy {
        viewModelOf(
            MapViewModel::class
        )
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_map,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapView.onCreate(savedInstanceState)

        viewModel.idSocialMedia = loadDataLogged()
        viewModel.idSocialMedia?.let { viewModel.getUserByIdFromApi(it) }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity as MainActivity)
        binding.mapView.getMapAsync(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    setCurrentLocation(googleMap)
                }
            }
        }
        startLocationUpdates()
        context?.let {
            Places.initialize(it, BuildConfig.MAPS_API_KEY)
            placesClient = Places.createClient(it)
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        setCurrentLocation(googleMap, true)
    }

    private fun startLocationUpdates() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(1000)
            .build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setCurrentLocation(googleMap: GoogleMap, isMovedCamera: Boolean = false) {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                val myCurrentLocation = LatLng(location.latitude, location.longitude)
                getCurrentPlace(googleMap, myCurrentLocation)
                if (isMovedCamera) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myCurrentLocation))
                }
            }
    }

    private fun getCurrentPlace(googleMap: GoogleMap, myCurrentLocation: LatLng) {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val placeFields = listOf(Place.Field.NAME, Place.Field.TYPES, Place.Field.LAT_LNG)
        val request = FindCurrentPlaceRequest.newInstance(placeFields)
        val placeResult = placesClient.findCurrentPlace(request)
        placeResult.addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val placeTagName = listOf(
                    "athletic_field",
                    "fitness_center",
                    "golf_course",
                    "gym",
                    "playground",
                    "fitness_center",
                    "ski_resort",
                    "sports_club",
                    "sports_complex",
                    "stadium",
                    "swimming_pool",
                    "amusement_center",
                    "amusement_park",
                    "aquarium",
                    "bowling_alley",
                    "dog_park",
                    "hiking_area",
                    "historical_landmark",
                    "marina",
                    "national_park",
                    "park",
                    "tourist_attraction",
                    "zoo"
                )

                val likelyPlaces = task.result
                val placeTypes = likelyPlaces.placeLikelihoods.firstOrNull()?.place?.placeTypes
                placeTypes?.let {
                    var foundPlace = false
                    for (placeType in it) {
                        placeTagName.find { tagName ->
                            placeType.equals(tagName)
                        }?.let {

                            val coordinateDomain = CoordinateDomain(
                                viewModel.idSocialMedia,
                                viewModel.loggedUserData?.firstName,
                                myCurrentLocation.latitude,
                                myCurrentLocation.longitude
                            )

                            viewModel.idSocialMedia?.let { idSocialMedia ->
                                viewModel.updateCoordinateByIdOnApi(idSocialMedia, coordinateDomain)
                            }

                            googleMap.addMarker(
                                MarkerOptions().position(myCurrentLocation)
                                    .title(viewModel.loggedUserData?.firstName)
                            )
                            foundPlace = true
                        } ?: run {
                            if (!foundPlace) {
                                googleMap.clear()
                                viewModel.idSocialMedia?.let { idSocialMedia ->
                                    viewModel.deleteCoordinateByIdOnApi(idSocialMedia)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadDataLogged(): String? {
        val sharedPreferences = context?.getSharedPreferences(
            SHARED_PREFERENCES,
            DaggerAppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences?.getString(ID_SOCIAL_MEDIA_KEY, EMPTY_STRING)
    }
}
package com.example.doctor.ui.location

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.doctor.R
import com.example.doctor.data.model.DirectionResponse
import com.example.doctor.data.model.RoutesItem
import com.example.doctor.data.source.ApiManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.fragment_hospitals_locations.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureNanoTime


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class HospitalsLocations : BaseLocationFragment(), OnMapReadyCallback {

    var hospitalMarker: Marker? = null
    var userMarker: Marker? = null

    var currentPolyline: Polyline? = null
    var map: GoogleMap? = null


    val path = ArrayList<LatLng>()

    lateinit var job: Job


    lateinit var dest: String
    var lat: Double? = null
    var lng: Double? = null

    lateinit var  result1:DirectionResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_hospitals_locations, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //api parameter
        dest = arguments?.get("dest") as String
        val str_origin = "${myLocation?.latitude}, ${myLocation?.longitude}"
        val key = getString(R.string.google_maps_key)

        lat = arguments?.get("lat") as Double
        lng = arguments?.get("lng") as Double








        map_view.onCreate(savedInstanceState)
        map_view.getMapAsync(this)

        //  val url = getUrl(userMarker?.position, hospitalMarker?.position, "driving")


        val handler = CoroutineExceptionHandler { _, exception ->
            println("Exception thrown in one of the children: $exception")
        }

        val parentJob = CoroutineScope(Default).launch(handler) {


            val time = measureNanoTime {

                 result1 =
                    withContext(Default) {
                        getDirectionsApi(str_origin, dest, key)
                    }

                val result2 =
                    withContext(Default) {

                        //  drawRoute(result1 as List<RoutesItem>)
                        drawPolyline(result1)

                    }
//
//                withContext(Default) {
//
//                  //  onPostExecute(result2)
//                }

            }


        }

    }

    suspend fun getDirectionsApi(
        str_origin: String,
        str_dest: String,
        key: String
    ): DirectionResponse {


        val res = ApiManager.getMapService().getDirection(str_origin, str_dest, key)


        return res


    }

    val result = ArrayList<List<LatLng>>()
    fun drawRoute(routes: List<RoutesItem>): ArrayList<List<LatLng>> {
        try {
            for (i in 0..(routes[0].legs?.get(0)?.steps?.size?.minus(1))!!) {
//                val start_latLng = LatLng(
//                    routes[0].legs?.get(0)?.steps?.get(i)?.startLocation!!.lat!!.toDouble(),
//                    routes[0].legs?.get(0)?.steps?.get(i)?.startLocation!!.lng!!.toDouble()
//                )
//
//                val start_latLng =
//                    LatLng( routes[0].legs?.get(0)?.steps?.get(i)?.startLocation!!.lat!!.toDouble()
//                            / 1E5, routes[0].legs?.get(0)?.steps?.get(i)?.startLocation!!.lng!!.toDouble() / 1E5)


//                path.addAll(decodePoly(routes[0].legs?.get(0)?.steps?.get(i)?.polyline?.points)!!)
//                path.addAll(decodePoly(routes[0].overviewPolyline?.points)!!)
//                path.addAll(PolyUtil(routes[0].overviewPolyline?.points)!!)

//                val end_latLng = LatLng(
//                    routes[0].legs?.get(0)?.steps?.get(i)?.endLocation!!.lat!!.toDouble(),
//                    routes[0].legs?.get(0)?.steps?.get(i)?.endLocation!!.lng!!.toDouble()
//                )
//                val end_latLng =
//                    LatLng( routes[0].legs?.get(0)?.steps?.get(i)?.endLocation!!.lat!!.toDouble()
//                            / 1E5, routes[0].legs?.get(0)?.steps?.get(i)?.endLocation!!.lng!!.toDouble() / 1E5)
//
//                path.add(end_latLng)
                result.add(path)
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), " ${e.localizedMessage}", Toast.LENGTH_LONG).show()

        }

        return result

    }

    suspend fun onPostExecute(result: List<List<LatLng>>) {
        val lineOptions = PolylineOptions()
        for (i in result.indices) {

            lineOptions.addAll(result[i])
            lineOptions.color(Color.BLUE)
            lineOptions.width(10f)

        }
        withContext(Main) {
            currentPolyline?.remove()
            currentPolyline = map?.addPolyline(lineOptions)


            //  map?.addPolyline(lineOptions)
        }
    }

    suspend fun drawPolyline(response: DirectionResponse) {

        if (myLocation!=null) {
            val shape = response.routes?.get(0)?.overviewPolyline?.points

            val polyline = PolylineOptions()
                .addAll(PolyUtil.decode(shape))
                .width(10f)
                .color(Color.BLUE)

            withContext(Main) {
                currentPolyline?.remove()
                currentPolyline = map?.addPolyline(polyline)
                println("dola shape $currentPolyline")
            }

        }
    }

    fun decodePoly(encoded: String?): List<LatLng>? {
        val poly: MutableList<LatLng> = ArrayList()
        var index = 0
        val len = encoded?.length
        var lat = 0
        var lng = 0
        while (index < len!!) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        addMarker()

    }

    fun addMarker() {
        if (myLocation == null || map == null)
            return

        if (userMarker == null)
            userMarker = map!!.addMarker(
                MarkerOptions()
                    .position(LatLng(myLocation!!.latitude, myLocation!!.longitude))
                    .title("You")
            )
        else userMarker!!.position = LatLng(myLocation!!.latitude, myLocation!!.longitude)

        if (hospitalMarker == null)
            hospitalMarker = map!!.addMarker(
                MarkerOptions()
                    .position(LatLng(lat!!,lng!!))
                    .title("Hospital")
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            generateBitmapDescriptorFromRes(
                                requireContext(),
                                R.drawable.hospital_icon
                            )
                        )
                    )
        )
        map!!.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(LatLng(lat!!, lng!!), 11f)
        )
    }

    fun generateBitmapDescriptorFromRes(
        context: Context?, resId: Int
    ): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), resId)
        drawable!!.setBounds(
            0,
            0,
            drawable.intrinsicWidth,
            drawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onStart() {
        super.onStart()
        map_view.onStart()
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()

    }

    override fun onStop() {
        super.onStop()
        map_view.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map_view.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map_view.onLowMemory()
    }


}

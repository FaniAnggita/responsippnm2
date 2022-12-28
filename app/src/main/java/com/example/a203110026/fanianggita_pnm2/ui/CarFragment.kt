package com.example.a203110026.fanianggita_pnm2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a203110026.fanianggita_pnm2.R
import com.example.a203110026.fanianggita_pnm2.databinding.DevbyteItemBinding
import com.example.a203110026.fanianggita_pnm2.databinding.FragmentDevByteBinding
import com.example.a203110026.fanianggita_pnm2.domain.CarModel
import com.example.a203110026.fanianggita_pnm2.viewmodels.CarViewModel

// TODO 9: Kelas Menampilkan list data ke fragment
class CarFragment : Fragment() {

    // mendelegasikan list data ke UI
    private val viewModel: CarViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, CarViewModel.Factory(activity.application))
                .get(CarViewModel::class.java)
    }

    //    Bagian recycle view untuk mengirimkan list data ke cards
    private var viewModelAdapter: DevByteAdapter? = null

    // onCreateView() dipanggil saat fragment sudah siap membaca sebuah layout.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<CarModel>> { videos ->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }
        })
    }


    // onCreate() diapnggil saat sebuah fragment dibuat (objeknya di memori).
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentDevByteBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dev_byte,
                container,
                false)

        // variabel untuk binding ke view
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        // Jika gambar diklik, maka akan membuka sebuah URL dari gambar tersebut
        viewModelAdapter = DevByteAdapter(VideoClick {

            val packageManager = context?.packageManager ?: return@VideoClick

            // untuk inten ke web browser
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if(intent.resolveActivity(packageManager) == null) {
                   intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.image_url_tumbnail))
            }

            startActivity(intent)
        })

        // membaca sebuah recycle view
        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            // set adapter dengan videomodel adapter
            adapter = viewModelAdapter
        }


        // viewmodel untuk membaca kesalahan jaringan
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

        //    Menampilkan toast jika jaringan error
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Kesalahan Jaringan!", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    //    Mengamblikan uri pada data gambar
    private val CarModel.launchUri: Uri
        get() {
            val httpUri = Uri.parse(image_url_tumbnail)
            return httpUri
        }
}


class VideoClick(val block: (CarModel) -> Unit) {
       fun onClick(video: CarModel) = block(video)
}

// Adaptor RecyclerView untuk menyiapkan pengikatan data pada item dalam daftar.
class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

//    Menampilkan adapter dari CarModel
    var videos: List<CarModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // Dipanggil saat RecyclerView membutuhkan viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: DevbyteItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                DevByteViewHolder.LAYOUT,
                parent,
                false)
        return DevByteViewHolder(withDataBinding)
    }

    // mengembalikan banyak data yang akan ditampilkan
    override fun getItemCount() = videos.size

    //    Memperbarui view holder
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

}

// ViewHolder untuk item data
class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.devbyte_item
    }
}
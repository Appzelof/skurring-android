package com.appzelof.skurring.radioObjects;

import android.provider.MediaStore;

import com.appzelof.skurring.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by daniel on 27/11/2017.
 */

public class RadioObjectListCreator {
    private ArrayList<RadioObject> radioArrayList;


    public ArrayList<RadioObject> getList(){

        radioArrayList = new ArrayList<>();


        radioArrayList.add(new RadioObject("NRK MP3" ,"http://lyd.nrk.no/nrk_radio_mp3_mp3_h", R.drawable.nrk_mp3));
        radioArrayList.add(new RadioObject("NRK Klassisk","http://lyd.nrk.no/nrk_radio_klassisk_mp3_h",R.drawable.nrk_klassisk));
        radioArrayList.add(new RadioObject("NRK Alltid Nyheter", "http://lyd.nrk.no/nrk_radio_alltid_nyheter_mp3_h",R.drawable.nrk_alltidnyheter));
        radioArrayList.add(new RadioObject("NRK Folkemusikk", "http://lyd.nrk.no/nrk_radio_folkemusikk_mp3_h", R.drawable.nrk_folkemusikk));
        radioArrayList.add(new RadioObject("NRK Sami", "http://lyd.nrk.no/nrk_radio_sami_mp3_h", R.drawable.nrk_sami));
        radioArrayList.add(new RadioObject("NRK Jazz", "http://lyd.nrk.no/nrk_radio_jazz_mp3_h", R.drawable.nrk_jazz));
        radioArrayList.add(new RadioObject("NRK Super", "http://lyd.nrk.no/nrk_radio_super_mp3_h", R.drawable.nrk_super));
        radioArrayList.add(new RadioObject("NRK Sport", "http://lyd.nrk.no/nrk_radio_sport_mp3_h", R.drawable.nrk_sport));
        radioArrayList.add(new RadioObject("NRK P13", "http://lyd.nrk.no/nrk_radio_p13_mp3_h",R.drawable.nrk_p13));
        radioArrayList.add(new RadioObject("NRK P1 Østlandssendingen", "http://lyd.nrk.no/nrk_radio_p1_ostlandssendingen_mp3_h", R.drawable.nrk_ost));
        radioArrayList.add(new RadioObject("NRK P1 Buskerud", "http://lyd.nrk.no/nrk_radio_p1_buskerud_mp3_h", R.drawable.nrk_buskerud));
        radioArrayList.add(new RadioObject("NRK P1 Finnmark", "http://lyd.nrk.no/nrk_radio_p1_finnmark_mp3_h", R.drawable.nrk_finnmark));
        radioArrayList.add(new RadioObject("NRK P1 Hordaland", "http://lyd.nrk.no/nrk_radio_p1_hordaland_mp3_h", R.drawable.nrk_hordaland));
        radioArrayList.add(new RadioObject("NRK P1 Møre og Romsdal", "http://lyd.nrk.no/nrk_radio_p1_more_og_romsdal_mp3_h", R.drawable.nrk_moreogroms));
        radioArrayList.add(new RadioObject("NRK P1 Nordland", "http://lyd.nrk.no/nrk_radio_p1_nordland_mp3_h", R.drawable.nrk_nordland));
        radioArrayList.add(new RadioObject("NRK P1 Hedmark og oppland", "http://lyd.nrk.no/nrk_radio_p1_hedmark_og_oppland_mp3_h", R.drawable.nrk_hedogop));
        radioArrayList.add(new RadioObject("NRK P1 Rogaland", "http://lyd.nrk.no/nrk_radio_p1_rogaland_mp3_h", R.drawable.nrk_rogaland));
        radioArrayList.add(new RadioObject("NRK P1 Sogn og Fjordane", "http://lyd.nrk.no/nrk_radio_p1_sogn_og_fjordane_mp3_h", R.drawable.nrk_sognogfjord));
        radioArrayList.add(new RadioObject("NRK P1 Sørlandet", "http://lyd.nrk.no/nrk_radio_p1_sorlandet_mp3_h", R.drawable.nrk_sorlandet));
        radioArrayList.add(new RadioObject("NRK P1 Telemark", "http://lyd.nrk.no/nrk_radio_p1_telemark_mp3_h", R.drawable.nrk_telemark));
        radioArrayList.add(new RadioObject("NRK P1 Troms", "http://lyd.nrk.no/nrk_radio_p1_troms_mp3_h", R.drawable.nrk_troms));
        radioArrayList.add(new RadioObject("NRK P1 Trøndelag", "http://lyd.nrk.no/nrk_radio_p1_trondelag_mp3_h", R.drawable.nrk_trond));
        radioArrayList.add(new RadioObject("NRK P1 Vestfold", "http://lyd.nrk.no/nrk_radio_p1_vestfold_mp3_h", R.drawable.nrk_vest));
        radioArrayList.add(new RadioObject("NRK P1 Østfold", "http://lyd.nrk.no/nrk_radio_p1_ostfold_mp3_h", R.drawable.nrk_ostfold));
        radioArrayList.add(new RadioObject("NRK P1+", "http://lyd.nrk.no/nrk_radio_p1pluss_mp3_h", R.drawable.nrkp1p));
        radioArrayList.add(new RadioObject("NRK P2", "http://lyd.nrk.no/nrk_radio_p2_mp3_h", R.drawable.nrk_p2));
        radioArrayList.add(new RadioObject("NRK P3", "http://lyd.nrk.no/nrk_radio_p3_mp3_h",R.drawable.p3));
        radioArrayList.add(new RadioObject("NRK P3 National Rapshow", "http://lyd.nrk.no/nrk_radio_p3_national_rap_show_mp3_h", R.drawable.nationalrap));
        radioArrayList.add(new RadioObject("NRK P3 Urørt", "http://lyd.nrk.no/nrk_radio_p3_urort_mp3_h", R.drawable.urort));
        radioArrayList.add(new RadioObject("NRK P3 Radioresepsjonen", "http://lyd.nrk.no/nrk_radio_p3_radioresepsjonen_mp3_h", R.drawable.radioresep));
        radioArrayList.add(new RadioObject("P4", "http://stream.p4.no/p4_mp3_hq", R.drawable.p4));
        radioArrayList.add(new RadioObject("P4 Bandit", "http://stream.p4.no/bandit_mp3_hq", R.drawable.bandit));
        radioArrayList.add(new RadioObject("P5 Nonstop Hits", "http://stream.p4.no/p5nonstophits_mp3_hq", R.drawable.p5_nonstophits));
        radioArrayList.add(new RadioObject("P5 Oslo", "http://stream.p4.no/p5oslo_mp3_hq", R.drawable.p5_hitsoslo));
        radioArrayList.add(new RadioObject("P5 Bergen", "http://stream.p4.no/p5bergen_mp3_hq",R.drawable.p5_berge));
        radioArrayList.add(new RadioObject("P5 Stavanger", "http://stream.p4.no/p5stavanger_mp3_hq", R.drawable.p5_stavanger));
        radioArrayList.add(new RadioObject("P5 Trondheim", "http://stream.p4.no/p5trondheim_mp3_hq", R.drawable.p5_hitstrondheim));
        radioArrayList.add(new RadioObject("P6 Rock", "http://stream.p4.no/p6_mp3_hq", R.drawable.p6_rock));
        radioArrayList.add(new RadioObject("P7 Klem", "http://stream.p4.no/p7_mp3_hq", R.drawable.p7_klem));
        radioArrayList.add(new RadioObject("P8 Pop", "http://stream.p4.no/p8_mp3_hq", R.drawable.p8_pop));
        radioArrayList.add(new RadioObject("P9 Retro", "http://stream.p4.no/p9_mp3_hq", R.drawable.p9_retro));
        radioArrayList.add(new RadioObject("P10 Country", "http://stream.p4.no/p10_mp3_hq", R.drawable.p10_country));
        radioArrayList.add(new RadioObject("Kiss", "http://stream.bauermedia.no/kiss.mp3", R.drawable.kiss));
        radioArrayList.add(new RadioObject("Kisstory", "http://stream.bauermedia.no/kisstory.mp3", R.drawable.kisstory));
        radioArrayList.add(new RadioObject("The Beat", "http://stream.thebeat.no/beat128.mp3", R.drawable.the_beat));
        radioArrayList.add(new RadioObject("Topp 40", "http://ads-e-bauerse-fm-03-cr.sharp-stream.com/top40_no_mp3?", R.drawable.topp_40));
        radioArrayList.add(new RadioObject("NRJ", "http://stream.p4.no/nrj_mp3_hq", R.drawable.nrj));
        radioArrayList.add(new RadioObject("Radio Norge", "http://stream2.sbsradio.no/radionorge.mp3", R.drawable.radio_norge));
        radioArrayList.add(new RadioObject("Radio Rock", "http://stream2.sbsradio.no/radiorock.mp3", R.drawable.radiorock));
        radioArrayList.add(new RadioObject("Radio Norge Soft", "http://stream.bauermedia.no/radionorgesoft.mp3", R.drawable.radiosoft));
        radioArrayList.add(new RadioObject("Radio 102", "http://downstream.radio.raw.no:8000/radio102",R.drawable.radio_102));
        radioArrayList.add(new RadioObject("Radio Trondheim", "http://nettradio.radiotrondheim.no:8000/lytte", R.drawable.radiotrondheim));
        radioArrayList.add(new RadioObject("Radio Revolt", "http://streamer.radiorevolt.no:8000/revolt", R.drawable.radiorevolt));
        radioArrayList.add(new RadioObject("Kristen Radio Vest", "http://5604.cloudrad.io:8268/kristenradiovest.mp3", R.drawable.kristenradio));
        radioArrayList.add(new RadioObject("Elverumsradioen", "http://109.169.96.11:8000/elverum.mp3", R.drawable.elverumsradioen));
        radioArrayList.add(new RadioObject("Østerdalsradioen", "http://109.169.96.11:8000/osterdal.mp3", R.drawable.osterdalsradioen));
        radioArrayList.add(new RadioObject("Solør+", "http://109.169.96.11:8000/solorpluss.mp3", R.drawable.solorp));
        radioArrayList.add(new RadioObject("Solor", "http://109.169.96.11:8000/solor.mp3", R.drawable.solor_radio));
        radioArrayList.add(new RadioObject("Trysilradioen", "http://109.169.96.11:8000/trysil.mp3", R.drawable.trysilradioen));
        radioArrayList.add(new RadioObject("Rox 90.1", "http://stream.radiorox.no:8040/listen", R.drawable.rox));
        radioArrayList.add(new RadioObject("Scansat", "http://stream.radioh.no:443/scan128", R.drawable.scansat));
        radioArrayList.add(new RadioObject("Ordentlig Radio", "http://mms-live.online.no/oradio_mp3_m", R.drawable.ordentlig));



        return radioArrayList;

    }



}

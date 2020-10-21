package com.project.eportfolio.APIService;

/**
 * Created by user on 1/10/2018.
 */


import com.project.eportfolio.model.grade.ModelGrade;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.ModelUpdateDataGuru;
import com.project.eportfolio.model.kategoristrategi.ModelKategoriStrategi;
import com.project.eportfolio.model.kelas.ModelKelas;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.ModelPostPortofolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.model.sekolah.ModelSekolah;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.user.ModelUser;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface APIInterfacesRest {

    // ------------------------- GET DATA -------------------------

    @GET("api/aauth_users/all")
    Call<ModelUser> getDataUser(@Query("X-Api-Key") String apikey,
                                @Query("limit") int limit);

    @GET("api/ms_murid/all")
    Call<ModelSiswa> getDataSiswa(@Query("X-Api-Key") String apikey,
                                  @Query("limit") int limit);

    @GET("api/ms_guru/all")
    Call<ModelGuru> getdataGuru(@Query("X-Api-Key") String apikey,
                                @Query("limit") int limit);

    @GET("api/ms_kelas/all")
    Call<ModelKelas> getdataKelas(@Query("X-Api-Key") String apikey,
                                  @Query("limit") int limit);

    @GET("api/ms_sekolah/all")
    Call<ModelSekolah> getDataSekolah(@Query("X-Api-Key") String apikey,
                                      @Query("limit") int limit);

    @GET("api/ms_matapelajaran/all")
    Call<ModelMataPelajaran> getDataMasterMapel(@Query("X-Api-Key") String apikey,
                                                @Query("limit") int limit);

    @GET("api/ms_kategoristrategi/all")
    Call<ModelKategoriStrategi> getDataKategoriStrategi(@Query("X-Api-Key") String apikey,
                                                        @Query("limit") int limit);

    @GET("api/ms_strategi/all")
    Call<ModelStrategi> getDataStrategi(@Query("X-Api-Key") String apikey,
                                        @Query("limit") int limit);

    @GET("api/ms_rubrik/all")
    Call<ModelMasterRubrik> getDataMasterRubrik(@Query("X-Api-Key") String apikey,
                                                @Query("limit") int limit);

    @GET("api/tr_portofolio/all")
    Call<ModelPortofolio> getDataPortfolio(@Query("X-Api-Key") String apikey,
                                           @Query("limit") int limit);

    @GET("api/ms_grade/all")
    Call<ModelGrade> getDataGrade(@Query("X-Api-Key") String apikey,
                                  @Query("limit") int limit);


    // ------------------------- POST DATA -------------------------


    @Multipart
    @POST("api/tr_portfolio/add")
    Call<ModelPostPortofolio> sendDataPortfolioSiswa(
            @Part("muridid") String muridid,
            @Part("guruid") String guruid,
            @Part("mapelid") String mapelid,
            @Part("strategiid") String strategiid,
            @Part("rubrikid") String rubrikid,
            @Part("judul_kd") String judul_kd,
            @Part("tanggal") String tanggal,
            @Part("tempat") String tempat,
            @Part("narasi") String narasi,
            @Part MultipartBody.Part foto,
            @Part("createdby") String createdby,
            @Part("createddate") String createddate,
            @Part("updateby") String updateby,
            @Part("updatedate") String updatedate
    );

    @Multipart
    @POST("api/tr_portfolio/add")
    Call<ModelPostPortofolio> sendDataPortfolioGuru(
            @Part("muridid") String muridid,
            @Part("guruid") String guruid,
            @Part("mapelid") String mapelid,
            @Part("idkategori") String idkategori,
            @Part("strategiid") String strategiid,
            @Part("rubrikid") String rubrikid,
            @Part("judul_kd") String judul_kd,
            @Part("rubrikdesk") String rubrikdesk,
            @Part("tanggal") String tanggal,
            @Part("tempat") String tempat,
            @Part("nilai") String nilai,
            @Part("predikat_mutu") String predikat_mutu,
            @Part("narasi") String narasi,
            @Part MultipartBody.Part foto,
            @Part("kelas") String kelas,
            @Part("th_ajaran") String th_ajaran,
            @Part("semester") String semester,
            @Part("createdby") String createdby,
            @Part("createddate") String createddate,
            @Part("updateby") String updateby,
            @Part("updatedate") String updatedate,
            @Part("predikat") String predikat,
            @Part("kelasid") String kelasid
    );


    // ------------------------- UPDATE DATA SISWA -------------------------


    @FormUrlEncoded
    @POST("api/ms_murid/update")
    Call<ModelUpdateDataSiswa> updateDataSiswaRequired(
            @Field("id") String id,
            @Field("userid") String userid,
            @Field("sekolahid") String sekolahid,
            @Field("kelasid") String kelasid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("nis") String nis,
            @Field("gender") String gender,
            @Field("ttl") String ttl,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phone") String phone
    );

    @Multipart
    @POST("api/ms_murid/update")
    Call<ModelUpdateDataSiswa> updateDataSiswaFoto(
            @Part("id") String id,
            @Part("userid") String userid,
            @Part("sekolahid") String sekolahid,
            @Part("kelasid") String kelasid,
            @Part("firstname") String firstname,
            @Part("midname") String midname,
            @Part("lastname") String lastname,
            @Part("ttl") String ttl,
            @Part("nis") String nis,
            @Part("gender") String gender,
            @Part("address") String address,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part  MultipartBody.Part photo
    );

    @FormUrlEncoded
    @POST("api/ms_murid/update")
    Call<ModelUpdateDataSiswa> updatePasswordSiswa(
            @Field("id") String id,
            @Field("userid") String userid,
            @Field("sekolahid") String sekolahid,
            @Field("kelasid") String kelasid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("ttl") String ttl,
            @Field("nis") String nis,
            @Field("gender") String gender,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );



    // ------------------------- UPDATE DATA GURU -------------------------


    @FormUrlEncoded
    @POST("api/ms_guru/update")
    Call<ModelUpdateDataGuru> updateDataGuruRequired(
            @Field("id_guru") String id_guru,
            @Field("userid") String userid,
            @Field("sekolahid") String sekolahid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("nik") String nik,
            @Field("nip") String nip,
            @Field("gender") String gender,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phone") String phone
    );


    @Multipart
    @POST("api/ms_guru/update")
    Call<ModelUpdateDataGuru> updateDataGuruFoto(
            @Part("id_guru") String id_guru,
            @Part("userid") String userid,
            @Part("sekolahid") String sekolahid,
            @Part("firstname") String firstname,
            @Part("midname") String midname,
            @Part("lastname") String lastname,
            @Part("nik") String nik,
            @Part("nip") String nip,
            @Part("gender") String gender,
            @Part("address") String address,
            @Part("email") String email,
            @Part("phone") String phone,
            @Part  MultipartBody.Part photo
    );


    @FormUrlEncoded
    @POST("api/ms_guru/update")
    Call<ModelUpdateDataGuru> updatePasswordGuru(
            @Field("id_guru") String id_guru,
            @Field("userid") String userid,
            @Field("sekolahid") String sekolahid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("nik") String nik,
            @Field("nip") String nip,
            @Field("gender") String gender,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );
}

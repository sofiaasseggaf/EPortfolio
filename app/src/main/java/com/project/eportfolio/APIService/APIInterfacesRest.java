package com.project.eportfolio.APIService;

/**
 * Created by user on 1/10/2018.
 */


import com.project.eportfolio.model.achievement.ModelAchievement;
import com.project.eportfolio.model.blog.ModelBlog;
import com.project.eportfolio.model.grade.ModelGrade;
import com.project.eportfolio.model.gradesekolah.ModelGradeSekolah;
import com.project.eportfolio.model.guru.ModelGuru;
import com.project.eportfolio.model.guru.ModelUpdateDataGuru;
import com.project.eportfolio.model.kategoristrategi.ModelKategoriStrategi;
import com.project.eportfolio.model.kelas.ModelKelas;
import com.project.eportfolio.model.matapelajaran.ModelMataPelajaran;
import com.project.eportfolio.model.portfolio.ModelPortofolio;
import com.project.eportfolio.model.portfolio.ModelPostPortfolio;
import com.project.eportfolio.model.rubrik.ModelMasterRubrik;
import com.project.eportfolio.model.siswa.ModelSiswa;
import com.project.eportfolio.model.siswa.ModelUpdateDataSiswa;
import com.project.eportfolio.model.sekolah.ModelSekolah;
import com.project.eportfolio.model.strategi.ModelStrategi;
import com.project.eportfolio.model.user.ModelUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @GET("api/tr_portofolio/all")
    Call<ModelPortofolio> getDataPortfolio2(@Query("X-Api-Key") String apikey,
                                           @Query("limit") int limit,
                                            @Query("filter") int filter,
                                            @Query("field") String field);

    @GET("api/ms_grade/all")
    Call<ModelGrade> getDataGrade(@Query("X-Api-Key") String apikey,
                                  @Query("limit") int limit);


    @GET("api/ms_grade_sekolah/all")
    Call<ModelGradeSekolah> getDataGradeSekolah(@Query("X-Api-Key") String apikey,
                                                @Query("limit") int limit);

    @GET("api/blog/all")
    Call<ModelBlog> getDataBlog(@Query("X-Api-Key") String apikey,
                                @Query("limit") int limit);


    @GET("api/achievment/all")
    Call<ModelAchievement> getDataAchievement(@Query("X-Api-Key") String apikey,
                                              @Query("limit") int limit);


    // ------------------------- POST DATA -------------------------


    @Multipart
    @POST("api/achievment/add")
    Call<ModelPostPortfolio> sendDataAchievement(
            @Header("X-Api-Key") RequestBody apikey,
            @Part("sekolahid") RequestBody sekolahid,
            @Part("muridid") RequestBody muridid,
            @Part("kategoripo") RequestBody kategoripo,
            @Part("judul") RequestBody judul,
            @Part("narasi") RequestBody narasi,
            @Part("tempat") RequestBody tempat,
            @Part("tanggal") RequestBody tanggal,
            @Part MultipartBody.Part foto
    );

    @Multipart
    @POST("api/tr_portofolio/add")
    Call<ModelPostPortfolio> sendDataPortfolioGuru(
            @Header("X-Api-Key") RequestBody apikey,
            @Part("muridid") RequestBody muridid,
            @Part("guruid") RequestBody guruid,
            @Part("mapelid") RequestBody mapelid,
            @Part("idkategori") RequestBody idkategori,
            @Part("strategiid") RequestBody strategiid,
            @Part("rubrikid") RequestBody rubrikid,
            @Part("judul_kd") RequestBody judul_kd,
            @Part("rubrikdesk") RequestBody rubrikdesk,
            @Part("tanggal") RequestBody tanggal,
            @Part("tempat") RequestBody tempat,
            @Part("nilai") RequestBody nilai,
            @Part("predikat_mutu") RequestBody predikat_mutu,
            @Part("narasi") RequestBody narasi,
            @Part MultipartBody.Part foto,
            @Part("kelas") RequestBody kelas,
            @Part("th_ajaran") RequestBody th_ajaran,
            @Part("semester") RequestBody semester,
            @Part("createdby") RequestBody createdby,
            @Part("createddate") RequestBody createddate,
            @Part("predikat") RequestBody predikat,
            @Part("kelasid") RequestBody kelasid
    );


    // ------------------------- UPDATE DATA SISWA -------------------------


    @FormUrlEncoded
    @POST("api/ms_murid/update")
    Call<ModelUpdateDataSiswa> updateDataSiswaRequired(
            @Header("X-Api-Key") String apikey,
            @Field("id") int id,
            @Field("userid") int userid,
            @Field("sekolahid") int sekolahid,
            @Field("kelasid") int kelasid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("nis") String nis,
            @Field("gender") String gender,
            @Field("ttl") String ttl,
            @Field("address") String address,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("updateby") String updateby,
            @Field("updatedate") String updatedate
    );

    @Multipart
    @POST("api/ms_murid/update")
    Call<ModelUpdateDataSiswa> updateDataSiswaRequiredFoto(
            @Header("X-Api-Key") RequestBody apikey,
            @Part("id") RequestBody id,
            @Part("userid") RequestBody userid,
            @Part("sekolahid") RequestBody sekolahid,
            @Part("kelasid") RequestBody kelasid,
            @Part("firstname") RequestBody firstname,
            @Part("midname") RequestBody midname,
            @Part("lastname") RequestBody lastname,
            @Part("nis") RequestBody nis,
            @Part("gender") RequestBody gender,
            @Part("ttl") RequestBody ttl,
            @Part("address") RequestBody address,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part  MultipartBody.Part photo,
            @Part("updateby") RequestBody updateby,
            @Part("updatedate") RequestBody updatedate
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
            @Header("X-Api-Key") String apikey,
            @Field("id_guru") int id_guru,
            @Field("userid") int userid,
            @Field("sekolahid") int sekolahid,
            @Field("firstname") String firstname,
            @Field("midname") String midname,
            @Field("lastname") String lastname,
            @Field("nik") String nik,
            @Field("nip") String nip,
            @Field("gender") String gender,
            @Field("address") String address,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("updateby") String updateby,
            @Field("updatedate") String updatedate

    );


    @Multipart
    @POST("api/ms_guru/update")
    Call<ModelUpdateDataGuru> updateDataGuruFoto(
            @Header("X-Api-Key") RequestBody  apikey,
            @Part("id_guru") RequestBody id_guru,
            @Part("userid") RequestBody  userid,
            @Part("sekolahid") RequestBody  sekolahid,
            @Part("firstname") RequestBody  firstname,
            @Part("midname") RequestBody  midname,
            @Part("lastname") RequestBody  lastname,
            @Part("nik") RequestBody  nik,
            @Part("nip") RequestBody  nip,
            @Part("gender") RequestBody  gender,
            @Part("address") RequestBody  address,
            @Part("email") RequestBody  email,
            @Part("phone") RequestBody  phone,
            @Part  MultipartBody.Part photo,
            @Part("updateby") RequestBody  updateby,
            @Part("updatedate") RequestBody  updatedate
    );

/*

    @FormUrlEncoded
    @POST("api/aauth_users/update")
    Call<ModelUpdateAauthUsers> updatePasswordGuru(

            @Header("X-Api-Key") String apikey,
            @Field("id_sekolah") String id_sekolah,
            @Field("email") String email,
            //@Field("group_id") String group_id,
            //@Field("oauth_uid") String oauth_uid,
            //@Field("oauth_provider") String oauth_provider,
            //@Field("pass") String pass,
            @Field("keypass") String keypass,
            @Field("username") String username,
            //@Field("full_name") String full_name,
            //@Field("avatar") String avatar,
            //@Field("foto") String foto,
            @Field("id") String id
    );

*/

}

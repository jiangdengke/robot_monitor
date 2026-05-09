/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.config.service;

import com.robotmonitor.config.domain.deepglint.changelist.ChangeListRequest;
import com.robotmonitor.config.domain.deepglint.changelist.ChangeListResponse;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.DeleteRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterRequest;
import com.robotmonitor.config.domain.deepglint.compare.ListRegisterResponse;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoRequest;
import com.robotmonitor.config.domain.deepglint.compare.RegisterPersonToCompareRepoResponse;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertRequest;
import com.robotmonitor.config.domain.deepglint.face.FaceHistoryAlertResponse;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListRequest;
import com.robotmonitor.config.domain.deepglint.facelist.FaceListResponse;

public interface IDeepGlintService {
    public RegisterPersonToCompareRepoResponse registerPersonToCompareRepo(RegisterPersonToCompareRepoRequest var1);

    public ListRegisterResponse listRegister(ListRegisterRequest var1);

    public DeleteRegisterResponse deleteRegister(DeleteRegisterRequest var1);

    public FaceListResponse queryFaceList(FaceListRequest var1);

    public FaceHistoryAlertResponse queryFaceHistoryAlert(FaceHistoryAlertRequest var1);

    public ChangeListResponse personChangelist(ChangeListRequest var1);
}

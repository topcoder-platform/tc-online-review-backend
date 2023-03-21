package com.topcoder.onlinereview.component.grpcclient.scorecard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.scorecard.Group;
import com.topcoder.onlinereview.component.scorecard.Question;
import com.topcoder.onlinereview.component.scorecard.QuestionType;
import com.topcoder.onlinereview.component.scorecard.Scorecard;
import com.topcoder.onlinereview.component.scorecard.ScorecardIDInfo;
import com.topcoder.onlinereview.component.scorecard.ScorecardStatus;
import com.topcoder.onlinereview.component.scorecard.ScorecardType;
import com.topcoder.onlinereview.component.scorecard.Section;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.grpc.scorecard.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ScorecardServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ScorecardServiceGrpc.ScorecardServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ScorecardServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public long createGroup(Group group, int order, String operator, long parentId) {
        CreateGroupRequest request = buildCreateGroupRequest(group, order, operator, parentId);
        GroupIdProto response = stub.createGroup(request);
        return response.getScorecardGroupId();
    }

    public List<Long> createGroups(Group[] groups, String operator, long parentId) {
        CreateGroupsRequest.Builder request = CreateGroupsRequest.newBuilder();
        for (int i = 0; i < groups.length; i++) {
            CreateGroupRequest req = buildCreateGroupRequest(groups[i], i, operator, parentId);
            request.addGroups(req);
        }
        GroupIdsProto response = stub.createGroups(request.build());
        return response.getScorecardGroupIdsList();
    }

    public int updateGroup(Group group, String operator, long parentId, int order) {
        UpdateGroupRequest.Builder request = UpdateGroupRequest.newBuilder();
        request.setScorecardGroupId(group.getId());
        request.setScorecardId(parentId);
        if (group.getName() != null) {
            request.setName(group.getName());
        }
        request.setWeight(group.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        CountProto response = stub.updateGroup(request.build());
        return response.getCount();
    }

    public List<Long> getScorecardSectionIds(Long[] ids) {
        GroupIdsProto request = GroupIdsProto.newBuilder().addAllScorecardGroupIds(Arrays.asList(ids)).build();
        SectionIdsProto response = stub.getScorecardSectionIds(request);
        return response.getScorecardSectionIdsList();
    }

    public int deleteGroups(Long[] ids) {
        GroupIdsProto request = GroupIdsProto.newBuilder().addAllScorecardGroupIds(Arrays.asList(ids)).build();
        CountProto response = stub.deleteGroups(request);
        return response.getCount();
    }

    public GetGroupResponse getGroup(long id) {
        GroupIdProto request = GroupIdProto.newBuilder().setScorecardGroupId(id).build();
        GetGroupResponse response = stub.getGroup(request);
        if (!response.hasScorecardGroupId()) {
            return null;
        }
        return response;
    }

    public List<GetGroupResponse> getGroups(Long parentId) {
        ScorecardIdProto.Builder request = ScorecardIdProto.newBuilder();
        if (parentId != null) {
            request.setScorecardId(parentId);
        }
        GetGroupsResponse response = stub.getGroups(request.build());
        return response.getGroupsList();
    }

    public long createQuestion(Question question, int order, String operator, long parentId) {
        CreateQuestionRequest request = buildCreateQuestionRequest(question, order, operator, parentId);
        QuestionIdProto response = stub.createQuestion(request);
        return response.getScorecardQuestionId();
    }

    public List<Long> createQuestions(Question[] questions, String operator, long parentId) {
        CreateQuestionsRequest.Builder request = CreateQuestionsRequest.newBuilder();
        for (int i = 0; i < questions.length; i++) {
            CreateQuestionRequest req = buildCreateQuestionRequest(questions[i], i, operator, parentId);
            request.addQuestions(req);
        }
        QuestionIdsProto response = stub.createQuestions(request.build());
        return response.getScorecardQuestionIdsList();
    }

    public int updateQuestion(Question question, int order, String operator, long parentId) {
        UpdateQuestionRequest.Builder request = UpdateQuestionRequest.newBuilder();
        request.setScorecardQuestionId(question.getId());
        if (question.getQuestionType() != null) {
            request.setScorecardQuestionTypeId(question.getQuestionType().getId());
        }
        request.setScorecardSectionId(parentId);
        if (question.getDescription() != null) {
            request.setDescription(question.getDescription());
        }
        if (question.getGuideline() != null) {
            request.setGuideline(question.getGuideline());
        }
        request.setWeight(question.getWeight());
        request.setSort(order);
        request.setUploadDocument(question.isUploadDocument());
        request.setUploadDocumentRequired(question.isUploadRequired());
        if (operator != null) {
            request.setOperator(operator);
        }
        CountProto response = stub.updateQuestion(request.build());
        return response.getCount();
    }

    public int deleteQuestions(Long[] ids) {
        QuestionIdsProto request = QuestionIdsProto.newBuilder().addAllScorecardQuestionIds(Arrays.asList(ids)).build();
        CountProto response = stub.deleteQuestions(request);
        return response.getCount();
    }

    public GetQuestionResponse getQuestion(long id) {
        QuestionIdProto request = QuestionIdProto.newBuilder().setScorecardQuestionId(id).build();
        GetQuestionResponse response = stub.getQuestion(request);
        if (!response.hasScorecardQuestionId()) {
            return null;
        }
        return response;
    }

    public List<GetQuestionResponse> getQuestions(Long parentId) {
        SectionIdProto.Builder request = SectionIdProto.newBuilder();
        if (parentId != null) {
            request.setScorecardSectionId(parentId);
        }
        GetQuestionsResponse response = stub.getQuestions(request.build());
        return response.getQuestionsList();
    }

    public long createScorecard(Scorecard scorecard, String operator, Date time) {
        CreateScorecardRequest request = buildCreateScorecardRequest(scorecard, operator, time);
        ScorecardIdProto response = stub.createScorecard(request);
        return response.getScorecardId();
    }

    public int updateScorecard(Scorecard scorecard, String operator, Date time, String version) {
        UpdateScorecardRequest.Builder request = UpdateScorecardRequest.newBuilder();
        request.setScorecardId(scorecard.getId());
        if (scorecard.getScorecardStatus() != null) {
            request.setScorecardStatusId(scorecard.getScorecardStatus().getId());
        }
        if (scorecard.getScorecardType() != null) {
            request.setScorecardTypeId(scorecard.getScorecardType().getId());
        }
        request.setProjectCategoryId(scorecard.getCategory());
        if (scorecard.getName() != null) {
            request.setName(scorecard.getName());
        }
        if (version != null) {
            request.setVersion(version);
        }
        request.setMinScore(scorecard.getMinScore());
        request.setMaxScore(scorecard.getMaxScore());
        if (operator != null) {
            request.setModifyUser(operator);
        }
        if (time != null) {
            request.setModifyDate(Timestamp.newBuilder().setSeconds(time.toInstant().getEpochSecond()).build());
        }
        CountProto response = stub.updateScorecard(request.build());
        return response.getCount();
    }

    public List<ScorecardType> getAllScorecardTypes() {
        GetAllScorecardTypesResponse response = stub.getAllScorecardTypes(null);
        List<ScorecardType> result = new ArrayList<>();
        for (ScorecardTypeProto s : response.getScorecardTypesList()) {
            ScorecardType scorecardType = new ScorecardType();
            if (s.hasScorecardTypeId()) {
                scorecardType.setId(s.getScorecardTypeId());
            }
            if (s.hasName()) {
                scorecardType.setName(s.getName());
            }
            result.add(scorecardType);
        }
        return result;
    }

    public List<QuestionType> getAllQuestionTypes() {
        GetAllQuestionTypesResponse response = stub.getAllQuestionTypes(null);
        List<QuestionType> result = new ArrayList<>();
        for (QuestionTypeProto q : response.getQuestionTypesList()) {
            QuestionType questionType = new QuestionType();
            if (q.hasScorecardQuestionTypeId()) {
                questionType.setId(q.getScorecardQuestionTypeId());
            }
            if (q.hasName()) {
                questionType.setName(q.getName());
            }
            result.add(questionType);
        }
        return result;
    }

    public List<ScorecardStatus> getAllScorecardStatuses() {
        GetAllScorecardStatusesResponse response = stub.getAllScorecardStatuses(null);
        List<ScorecardStatus> result = new ArrayList<>();
        for (ScorecardStatusProto s : response.getScorecardStatusesList()) {
            ScorecardStatus scorecardStatus = new ScorecardStatus();
            if (s.hasScorecardStatusId()) {
                scorecardStatus.setId(s.getScorecardStatusId());
            }
            if (s.hasName()) {
                scorecardStatus.setName(s.getName());
            }
            result.add(scorecardStatus);
        }
        return result;
    }

    public List<ScorecardIDInfo> getDefaultScorecardsIDInfo(long projectCategoryId) {
        ProjectCategoryIdProto request = ProjectCategoryIdProto.newBuilder().setProjectCategoryId(projectCategoryId)
                .build();
        GetDefaultScorecardsIDInfoResponse response = stub.getDefaultScorecardsIDInfo(request);
        List<ScorecardIDInfo> result = new ArrayList<>();
        for (ScorecardsIDInfoProto s : response.getScorecardIdInfosList()) {
            result.add(new ScorecardIDInfo(s.getScorecardTypeId(), s.getScorecardId()));
        }
        return result;
    }

    public List<ScorecardProto> getScorecards(Long[] ids) {
        ScorecardIdsProto request = ScorecardIdsProto.newBuilder().addAllScorecardIds(Arrays.asList(ids)).build();
        GetScorecardsResponse response = stub.getScorecards(request);
        return response.getScorecardsList();
    }

    public List<Long> getScorecardsInUse(Long[] ids) {
        ScorecardIdsProto request = ScorecardIdsProto.newBuilder().addAllScorecardIds(Arrays.asList(ids)).build();
        ScorecardIdsProto response = stub.getScorecardsInUse(request);
        return response.getScorecardIdsList();
    }

    public long createSection(Section section, int order, String operator, long parentId) {
        CreateSectionRequest request = buildCreateSectionRequest(section, order, operator, parentId);
        SectionIdProto response = stub.createSection(request);
        return response.getScorecardSectionId();
    }

    public List<Long> createSections(Section[] sections, String operator, long parentId) {
        CreateSectionsRequest.Builder request = CreateSectionsRequest.newBuilder();
        for (int i = 0; i < sections.length; i++) {
            CreateSectionRequest req = buildCreateSectionRequest(sections[i], i, operator, parentId);
            request.addSections(req);
        }
        SectionIdsProto response = stub.createSections(request.build());
        return response.getScorecardSectionIdsList();
    }

    public int updateSection(Section section, String operator, long parentId, int order) {
        UpdateSectionRequest.Builder request = UpdateSectionRequest.newBuilder();
        request.setScorecardSectionId(section.getId());
        request.setScorecardGroupId(parentId);
        if (section.getName() != null) {
            request.setName(section.getName());
        }
        request.setWeight(section.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        CountProto response = stub.updateSection(request.build());
        return response.getCount();
    }

    public List<Long> getQuestionIds(Long[] ids) {
        SectionIdsProto request = SectionIdsProto.newBuilder().addAllScorecardSectionIds(Arrays.asList(ids)).build();
        QuestionIdsProto response = stub.getQuestionIds(request);
        return response.getScorecardQuestionIdsList();
    }

    public int deleteSections(Long[] ids) {
        SectionIdsProto request = SectionIdsProto.newBuilder().addAllScorecardSectionIds(Arrays.asList(ids)).build();
        CountProto response = stub.deleteSections(request);
        return response.getCount();
    }

    public GetSectionResponse getSection(long id) {
        SectionIdProto request = SectionIdProto.newBuilder().setScorecardSectionId(id).build();
        GetSectionResponse response = stub.getSection(request);
        if (!response.hasScorecardSectionId()) {
            return null;
        }
        return response;
    }

    public List<GetSectionResponse> getSections(Long parentId) {
        GroupIdProto.Builder request = GroupIdProto.newBuilder();
        if (parentId != null) {
            request.setScorecardGroupId(parentId);
        }
        GetSectionsResponse response = stub.getSections(request.build());
        return response.getSectionsList();
    }

    public List<ScorecardProto> searchScorecards(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetScorecardsResponse response = stub.searchScorecards(filterProto);
        return response.getScorecardsList();
    }

    private CreateGroupRequest buildCreateGroupRequest(Group group, int order, String operator, long parentId) {
        CreateGroupRequest.Builder request = CreateGroupRequest.newBuilder();
        request.setScorecardId(parentId);
        if (group.getName() != null) {
            request.setName(group.getName());
        }
        request.setWeight(group.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        return request.build();
    }

    private CreateQuestionRequest buildCreateQuestionRequest(Question question, int order, String operator,
            long parentId) {
        CreateQuestionRequest.Builder request = CreateQuestionRequest.newBuilder();
        if (question.getQuestionType() != null) {
            request.setScorecardQuestionTypeId(question.getQuestionType().getId());
        }
        request.setScorecardSectionId(parentId);
        if (question.getDescription() != null) {
            request.setDescription(question.getDescription());
        }
        if (question.getGuideline() != null) {
            request.setGuideline(question.getGuideline());
        }
        request.setWeight(question.getWeight());
        request.setSort(order);
        request.setUploadDocument(question.isUploadDocument());
        request.setUploadDocumentRequired(question.isUploadRequired());
        if (operator != null) {
            request.setOperator(operator);
        }
        return request.build();
    }

    private CreateScorecardRequest buildCreateScorecardRequest(Scorecard scorecard, String operator, Date time) {
        CreateScorecardRequest.Builder request = CreateScorecardRequest.newBuilder();
        if (scorecard.getScorecardStatus() != null) {
            request.setScorecardStatusId(scorecard.getScorecardStatus().getId());
        }
        if (scorecard.getScorecardType() != null) {
            request.setScorecardTypeId(scorecard.getScorecardType().getId());
        }
        request.setProjectCategoryId(scorecard.getCategory());
        if (scorecard.getName() != null) {
            request.setName(scorecard.getName());
        }
        if (scorecard.getVersion() != null) {
            request.setVersion(scorecard.getVersion());
        }
        request.setMinScore(scorecard.getMinScore());
        request.setMaxScore(scorecard.getMaxScore());
        if (operator != null) {
            request.setCreateUser(operator);
            request.setModifyUser(operator);
        }
        if (time != null) {
            request.setCreateDate(Timestamp.newBuilder().setSeconds(time.toInstant().getEpochSecond()).build());
            request.setModifyDate(Timestamp.newBuilder().setSeconds(time.toInstant().getEpochSecond()).build());
        }
        return request.build();
    }

    private CreateSectionRequest buildCreateSectionRequest(Section section, int order, String operator, long parentId) {
        CreateSectionRequest.Builder request = CreateSectionRequest.newBuilder();
        request.setScorecardGroupId(parentId);
        if (section.getName() != null) {
            request.setName(section.getName());
        }
        request.setWeight(section.getWeight());
        request.setSort(order);
        if (operator != null) {
            request.setOperator(operator);
        }
        return request.build();
    }
}

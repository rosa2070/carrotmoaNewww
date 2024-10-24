package carrotmoa.carrotmoa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHostAdditionalForm is a Querydsl query type for HostAdditionalForm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHostAdditionalForm extends EntityPathBase<HostAdditionalForm> {

    private static final long serialVersionUID = -529283526L;

    public static final QHostAdditionalForm hostAdditionalForm = new QHostAdditionalForm("hostAdditionalForm");

    public final NumberPath<Integer> account = createNumber("account", Integer.class);

    public final StringPath accountHolder = createString("accountHolder");

    public final StringPath bankName = createString("bankName");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> updatedAt = createDateTime("updatedAt", java.time.Instant.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QHostAdditionalForm(String variable) {
        super(HostAdditionalForm.class, forVariable(variable));
    }

    public QHostAdditionalForm(Path<? extends HostAdditionalForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHostAdditionalForm(PathMetadata metadata) {
        super(HostAdditionalForm.class, metadata);
    }

}

